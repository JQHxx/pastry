package com.mrl.pastry.portal.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrl.pastry.common.utils.SecurityUtils;
import com.mrl.pastry.portal.constant.RedisConstant;
import com.mrl.pastry.portal.model.entity.Blog;
import com.mrl.pastry.portal.model.vo.BlogVO;
import com.mrl.pastry.portal.service.BlogService;
import com.mrl.pastry.portal.service.RankService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mrl.pastry.portal.constant.RedisConstant.BLOG_RANK;

/**
 * RankService implementation
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/1
 */
@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {

    private final RedissonClient redissonClient;
    private final BlogService blogService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public IPage<BlogVO> getRankList() {
        IPage<BlogVO> page = new Page<>();
        Set<Object> blogs = redisTemplate.opsForZSet().reverseRange(BLOG_RANK, 0, RANGE - 1);
        if (CollectionUtils.isEmpty(blogs)) {
            return page.setRecords(Collections.emptyList());
        }
        List<BlogVO> list = blogs.stream().map(blogId -> {
            Blog blog = blogService.getOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, blogId)
                    .select(Blog::getId, Blog::getAuthor));
            return blogService.convertToBlogVO(blog, SecurityUtils.getCurrentUserId());
        }).collect(Collectors.toList());
        return page.setRecords(list);
    }

    @Override
    public void offer(@NonNull Long blogId, @NonNull Integer delta) {
        RLock lock = redissonClient.getLock(RedisConstant.RANK_LOCK);
        lock.lock();
        try {
            Double score = redisTemplate.opsForZSet().score(BLOG_RANK, blogId);
            if (Objects.isNull(score)) {
                // set the value(blogId) with score(current-time + delta)
                redisTemplate.opsForZSet().add(BLOG_RANK, blogId, System.currentTimeMillis() + delta);
                Long size = redisTemplate.opsForZSet().zCard(BLOG_RANK);
                if (!Objects.isNull(size) && size > RANGE) {
                    // remove the blog with lowest score
                    redisTemplate.opsForZSet().removeRange(BLOG_RANK, 0, 0);
                }
            } else {
                redisTemplate.opsForZSet().incrementScore(BLOG_RANK, blogId, delta);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void remove(@NonNull Long blogId) {
        redisTemplate.opsForZSet().remove(BLOG_RANK, blogId);
    }
}
