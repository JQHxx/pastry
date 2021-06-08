package com.mrl.pastry.portal.constant;

/**
 * Redis constants
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/7
 */
public interface RedisConstant {

    /**
     * 用KV存用户信息
     */
    String USER_NAMESPACE = "user";

    /**
     * 用KV存博客内容
     */
    String BLOG_CONTENT_NAMESPACE = "blog:content";

    /**
     * 用Hash存点赞记录
     * key["blog:like:n"] hashKey["blogId:userId"] value[0/1]
     */
    String BLOG_LIKE_NAMESPACE = "blog:like:";
    /**
     * 用于拆分点赞大key
     */
    Integer BLOG_PARTITION = 20;

    /**
     * 用Set存浏览记录
     * key["blog:visit:blogId"] value["userId"]
     */
    String BLOG_VISIT_NAMESPACE = "blog:visit:";

    /**
     * 用Hash存统计数据
     * key["blog:statistic:blogId"] hashKey["like","visit"] value[count]
     */
    String BLOG_STATISTIC_NAMESPACE = "blog:statistic:";
    String COUNT_LIKE = "like";
    String COUNT_VISIT = "visit";

    /**
     * 用Z-Set存排行榜信息
     */
    String BLOG_RANK = "blog:rank";
    Integer COIN_SCORE = 4320_000;

    /**
     * Redisson lock
     */
    String RANK_LOCK = "rank-lock";
    String LIKE_LOCK = "like-lock";
}
