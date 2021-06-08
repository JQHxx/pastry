package com.mrl.pastry.portal.task;

import com.mrl.pastry.portal.constant.RedisConstant;
import com.mrl.pastry.portal.mapper.BlogMapper;
import com.mrl.pastry.portal.model.entity.UserLike;
import com.mrl.pastry.portal.model.enums.TargetType;
import com.mrl.pastry.portal.service.UserLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * Blog statistic task
 * 后续整合xxl-job
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/2
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BlogStatisticTask {

    private final UserLikeService userLikeService;
    private final BlogMapper blogMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "executor")
    private TaskExecutor taskExecutor;

    /**
     * 每小时 将浏览量、点赞数更新入库
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void statistic() {
        try {
            String namespace = RedisConstant.BLOG_STATISTIC_NAMESPACE;
            ScanOptions options = ScanOptions.scanOptions().match(namespace + "*").count(10000).build();
            Cursor<?> cursor = redisTemplate.executeWithStickyConnection(connection ->
                    new ConvertingCursor<>(connection.scan(options), redisTemplate.getKeySerializer()::deserialize));
            if (cursor != null) {
                while (cursor.hasNext()) {
                    String key = (String) cursor.next();
                    if (key != null) {
                        String blogId = key.substring(namespace.length());

                        // update statistic data
                        Integer like = (Integer) redisTemplate.opsForHash().get(key, RedisConstant.COUNT_LIKE);
                        Integer visit = (Integer) redisTemplate.opsForHash().get(key, RedisConstant.COUNT_VISIT);
                        // 更新非空值
                        blogMapper.updateBlogStatistic(Long.valueOf(blogId), like, visit);

                        // delete hashKey
                        redisTemplate.opsForHash().delete(key, RedisConstant.COUNT_LIKE);
                        redisTemplate.opsForHash().delete(key, RedisConstant.COUNT_VISIT);
                    }
                }
                cursor.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 每小时 将点赞记录入库
     */
    @Scheduled(cron = "0 0 0/3 * * ?")
    public void like() {
        try {
            for (int i = 0; i < RedisConstant.BLOG_PARTITION; i++) {
                String key = RedisConstant.BLOG_LIKE_NAMESPACE + i;
                ScanOptions options = ScanOptions.scanOptions().match("*").count(10000).build();
                Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, options);
                while (cursor.hasNext()) {
                    Map.Entry<Object, Object> next = cursor.next();

                    String hashKey = (String) next.getKey();
                    String[] ids = hashKey.split(":");
                    Long blogId = Long.valueOf(ids[0]);
                    Long userId = Long.valueOf(ids[1]);

                    Integer like = (Integer) next.getValue();
                    userLikeService.saveRecord(new UserLike(userId, blogId, TargetType.BLOG, like == 1));

                    redisTemplate.opsForHash().delete(key, hashKey);
                }
                cursor.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 每天 清空浏览量
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void visit() {
        try {
            String namespace = RedisConstant.BLOG_VISIT_NAMESPACE;
            ScanOptions options = ScanOptions.scanOptions().match(namespace + "*").count(Integer.MAX_VALUE).build();
            Cursor<?> cursor = redisTemplate.executeWithStickyConnection(connection ->
                    new ConvertingCursor<>(connection.scan(options), redisTemplate.getKeySerializer()::deserialize));
            if (cursor != null) {
                while (cursor.hasNext()) {
                    String key = (String) cursor.next();
                    if (key != null) {
                        // 暂不存储浏览记录
                        redisTemplate.delete(key);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


