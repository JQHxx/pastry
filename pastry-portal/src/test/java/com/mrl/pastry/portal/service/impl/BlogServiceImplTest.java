package com.mrl.pastry.portal.service.impl;

import com.mrl.pastry.portal.service.UserLikeService;
import com.mrl.pastry.portal.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;

/**
 * Blog service test
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/2
 */
@SpringBootTest
public class BlogServiceImplTest {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private UserLikeService userLikeService;

    @Test
    public void test() {
        long userId = 1376800726310223873L;
        long blogId = 1376869749441990657L;

        StopWatch stopWatch = new StopWatch("give a like");
        stopWatch.start();


        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

}
