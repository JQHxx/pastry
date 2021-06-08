package com.mrl.pastry.portal.listener;

import com.mrl.pastry.portal.constant.RedisConstant;
import com.mrl.pastry.portal.event.blog.BlogCoinEvent;
import com.mrl.pastry.portal.event.blog.BlogVisitEvent;
import com.mrl.pastry.portal.mapper.BlogMapper;
import com.mrl.pastry.portal.service.RankService;
import com.mrl.pastry.portal.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Blog visit event listener
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/14
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BlogEventListener {

    private final RedisUtils redisUtils;
    private final BlogMapper blogMapper;
    private final RankService rankService;
    //private final BlogRepository blogRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

//    @Async
//    @EventListener
//    public void onApplicationEvent(BlogNewEvent event) {
//        Blog blog = event.getBlog();
//        // save blog document
//        BlogDocument doc = BeanUtils.copyProperties(blog, BlogDocument.class);
//        blogRepository.save(doc);
//        log.debug("es saved blog document: [{}]", doc);
//    }

    @Async
    @EventListener
    public void onApplicationEvent(BlogVisitEvent event) {
        Long blogId = event.getBlogId();
        String visitKey = RedisConstant.BLOG_VISIT_NAMESPACE + blogId;
        Long add = redisTemplate.opsForSet().add(visitKey, event.getUserId());
        if (!Objects.isNull(add) && add != 0) {
            // increase the visit count
            redisUtils.increase(RedisConstant.BLOG_STATISTIC_NAMESPACE + blogId,
                    RedisConstant.COUNT_VISIT, () -> blogMapper.getVisitCount(blogId), add);
        }
    }

    @Async
    @EventListener
    public void onApplicationEvent(BlogCoinEvent event) {
        // increase rank score
        rankService.offer(event.getBlogId(), RedisConstant.COIN_SCORE);
    }

//    @Async
//    @EventListener
//    public void onApplicationEvent(BlogDeleteEvent event) {
//        Long blogId = event.getBlogId();
//        // delete blog document
//        blogRepository.deleteById(blogId);
//        log.debug("es delete blog document: [{}]", blogId);
//    }
}
