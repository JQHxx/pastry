package com.mrl.pastry.portal.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.common.exception.BadRequestException;
import com.mrl.pastry.common.utils.BeanUtils;
import com.mrl.pastry.common.utils.PageUtils;
import com.mrl.pastry.common.utils.SecurityUtils;
import com.mrl.pastry.portal.constant.RedisConstant;
import com.mrl.pastry.portal.event.blog.BlogCoinEvent;
import com.mrl.pastry.portal.event.blog.BlogDeleteEvent;
import com.mrl.pastry.portal.event.blog.BlogNewEvent;
import com.mrl.pastry.portal.event.blog.BlogVisitEvent;
import com.mrl.pastry.portal.feign.FeignUploadService;
import com.mrl.pastry.portal.mapper.BlogMapper;
import com.mrl.pastry.portal.mapper.UserMapper;
import com.mrl.pastry.portal.model.dto.BlogBaseDTO;
import com.mrl.pastry.portal.model.dto.UserBaseDTO;
import com.mrl.pastry.portal.model.entity.Blog;
import com.mrl.pastry.portal.model.enums.BlogStatus;
import com.mrl.pastry.portal.model.params.BlogPostParam;
import com.mrl.pastry.portal.model.vo.BlogStatisticVO;
import com.mrl.pastry.portal.model.vo.BlogVO;
import com.mrl.pastry.portal.service.BlogService;
import com.mrl.pastry.portal.service.RankService;
import com.mrl.pastry.portal.service.UserLikeService;
import com.mrl.pastry.portal.service.UserService;
import com.mrl.pastry.portal.utils.RedisUtils;
import com.mrl.pastry.portal.utils.SensitiveUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * BlogService implementation
 *
 * @author MrL
 * @since 2021-03-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserService userService;

    private final UserMapper userMapper;

    private final UserLikeService userLikeService;

    @Autowired
    private FeignUploadService uploadService;

    private final RedisUtils redisUtils;
    private final RedissonClient redissonClient;

    private final SensitiveUtils sensitiveUtils;

    @Autowired
    private RankService rankService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public IPage<BlogVO> getPage(Pageable pageable, Long limit, Long userId) {
        Page<Blog> page = PageUtils.convertToPage(pageable);
        // 优化：索引(author) + 缓存
        // query the latest page, select blogId、userId
        LambdaQueryWrapper<Blog> queryWrapper = Wrappers.<Blog>lambdaQuery()
                .select(Blog::getId, Blog::getAuthor);
        if (!Objects.isNull(userId) && userId != 0) {
            // query the personal blogs
            queryWrapper.eq(Blog::getAuthor, userId);
        }
        if (!Objects.isNull(limit) && limit != 0) {
            // query the previous page
            queryWrapper.lt(Blog::getId, limit);
        }
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return page(page, queryWrapper).convert(blog -> this.convertToBlogVO(blog, currentUserId));
    }

    @Override
    public void post(@NonNull BlogPostParam blogParam) {
        Long userId = SecurityUtils.getCurrentUserId();
        Blog blog = blogParam.convertTo();
        blog.setAuthor(userId);
        // set attachments jsonStr, 交给前端parse
        List<String> attachments = blogParam.getAttachmentList();
        List<String> subList = ListUtil.sub(attachments, 0, MAX_ATTACHMENTS);
        blog.setAttachments(JSONUtil.toJsonStr(subList));
        // exclude sensitive words
        String content = sensitiveUtils.refine(blogParam.getContent());
        blog.setContent(content);
        // TODO: admin module 审核
        blog.setStatus(BlogStatus.PUBLISHED);
        save(blog);
        log.debug("saved a blog: [{}]", blog);

        // 优化: 冗余代替count(*)
        userMapper.increaseBlogCount(blog.getAuthor(), 1);

        // publish blog-new event
        eventPublisher.publishEvent(new BlogNewEvent(this, blog));
    }

    @Override
    public BlogVO convertToBlogVO(@NonNull Blog blog, @NonNull Long currentUserId) {
        Assert.notNull(blog.getId(), "blogId must not be null");
        Assert.notNull(blog.getAuthor(), "author must not be null");

        // get cached blogBaseDTO
        BlogBaseDTO baseDTO = this.getBlogBaseDTO(blog.getId());
        BlogVO blogVO = BeanUtils.copyProperties(baseDTO, BlogVO.class);
        // get cached statistics
        BlogStatisticVO statistics = this.getBlogStatistic(blog.getId());
        blogVO.setStatistic(statistics);
        // get cached user data
        UserBaseDTO user = userService.getUserDto(blog.getAuthor());
        blogVO.setUser(user);
        // set like status
        blogVO.setLike(checkLike(blog.getId(), currentUserId));

        // publish visit event
        eventPublisher.publishEvent(new BlogVisitEvent(this, blog.getId(), currentUserId));
        return blogVO;
    }

    @Override
    public BlogBaseDTO getBlogBaseDTO(@NonNull Long blogId) {
        return redisUtils.cacheable(RedisConstant.BLOG_CONTENT_NAMESPACE, blogId + "",
                () -> this.baseMapper.getBlogBaseDTO(blogId), BlogBaseDTO.class);
    }

    @Override
    public BlogStatisticVO getBlogStatistic(@NonNull Long blogId) {
        // cached data
        Integer visit = redisUtils.cacheIfAbsent(RedisConstant.BLOG_STATISTIC_NAMESPACE + blogId,
                RedisConstant.COUNT_VISIT, () -> this.baseMapper.getVisitCount(blogId));
        Integer like = redisUtils.cacheIfAbsent(RedisConstant.BLOG_STATISTIC_NAMESPACE + blogId,
                RedisConstant.COUNT_LIKE, () -> this.baseMapper.getLikeCount(blogId));
        // db data
        Integer comment = this.baseMapper.getCommentCount(blogId);
        Integer coin = this.baseMapper.getCoinCount(blogId);
        return new BlogStatisticVO(visit, coin, comment, like);
    }

    @Override
    public void deleteBlog(@NonNull Long blogId) {
        // remove from blog rank
        rankService.remove(blogId);
        // remove cached content
        redisUtils.evict(RedisConstant.BLOG_CONTENT_NAMESPACE, blogId + "");

        Long userId = SecurityUtils.getCurrentUserId();
        Blog blog = getOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, blogId).eq(Blog::getAuthor, userId)
                .select(Blog::getId, Blog::getAttachments));
        Optional.ofNullable(blog).orElseThrow(() -> new BadRequestException("删除失败!"));

        boolean delete = remove(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, blogId));
        log.debug("user:[{}] delete a blog:[{}], result:[{}]", userId, blogId, delete);
        if (delete) {
            // decrease user blog_count
            userMapper.increaseBlogCount(userId, -1);

            String attachments = blog.getAttachments();
            if (!StringUtils.isEmpty(attachments)) {
                // 提取除去域名/bucketName后的文件名
                List<String> fileKeys = JSONUtil.parseArray(attachments).toList(String.class).stream()
                        .map(accessPath -> accessPath.substring(accessPath.lastIndexOf("/") + 1))
                        .collect(Collectors.toList());
                uploadService.deleteAttachments(fileKeys);
            }

            // publish blog-delete event
            eventPublisher.publishEvent(new BlogDeleteEvent(this, blogId));
        }
    }

    @Override
    public Boolean doLikeBlog(@NonNull Long blogId) {
        Assert.notNull(blogId, "blogId must not be null");
        // 必须加锁保证数据一致，吞吐量140/sec左右
        RLock lock = redissonClient.getLock(RedisConstant.LIKE_LOCK);
        lock.lock();
        try {
            Long userId = SecurityUtils.getCurrentUserId();
            // 这里获取相反的状态
            boolean like = !checkLike(blogId, userId);
            String key = RedisConstant.BLOG_LIKE_NAMESPACE + (blogId % RedisConstant.BLOG_PARTITION);
            String hashKey = blogId + ":" + userId;
            // 根据取反后的状态，更新统计值
            redisTemplate.opsForHash().put(key, hashKey, like ? 1 : 0);
            redisUtils.increase(RedisConstant.BLOG_STATISTIC_NAMESPACE + blogId,
                    RedisConstant.COUNT_LIKE, () -> this.baseMapper.getLikeCount(blogId), like ? 1 : -1);
            return like;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Boolean checkLike(@NonNull Long blogId, @NonNull Long userId) {
        // e.g. key [blog:like n]，hashKeys [blog1:user1]..., value [1/0]
        String key = RedisConstant.BLOG_LIKE_NAMESPACE + (blogId % RedisConstant.BLOG_PARTITION);
        String hashKey = blogId + ":" + userId;
        Integer value = (Integer) redisTemplate.opsForHash().get(key, hashKey);
        return Optional.ofNullable(value).map(v -> v == 1).orElseGet(() -> {
            Boolean record = userLikeService.isLike(userId, blogId);
            redisTemplate.opsForHash().put(key, hashKey, record ? 1 : 0);
            return record;
        });
    }

    @Override
    public void doGiveCoin(@NonNull Long blogId) {
        Assert.notNull(blogId, "blogId must not be null");
        Optional.ofNullable(getOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, blogId).select(Blog::getId)))
                .orElseThrow(() -> new BadRequestException("投币主体不存在!"));
        userService.decreaseCoinCount(1);
        this.baseMapper.increaseCoinCount(blogId, 1);
        // publish blog-coin event
        eventPublisher.publishEvent(new BlogCoinEvent(this, blogId));
    }
}
