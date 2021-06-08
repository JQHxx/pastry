package com.mrl.pastry.portal.utils;

import cn.hutool.core.util.BooleanUtil;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Redis utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisCacheManager cacheManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用hash缓存博客浏览量与点赞数
     *
     * @param key      blog statistic namespace
     * @param hashKey  hash key: blogId
     * @param supplier gets a result from db
     * @return cached value
     */
    public Integer cacheIfAbsent(@NonNull String key, @NonNull String hashKey, @NonNull Supplier<Integer> supplier) {
        Integer result = (Integer) redisTemplate.opsForHash().get(key, hashKey);
        return Optional.ofNullable(result).orElseGet(() -> {
            Integer data = supplier.get();
            redisTemplate.opsForHash().put(key, hashKey, data);
            return data;
        });
    }

    /**
     * 统计操作先走redis, 后续通过执行计划刷回DB
     *
     * @param key      blog statistic namespace
     * @param hashKey  hash key: blogId
     * @param supplier gets a result from db
     * @param delta    1 / -1 / 0
     */
    public void increase(@NonNull String key, @NonNull String hashKey, Supplier<Integer> supplier, long delta) {
        Boolean exist = redisTemplate.opsForHash().hasKey(key, hashKey);
        if (BooleanUtil.isFalse(exist)) {
            redisTemplate.opsForHash().put(key, hashKey, supplier.get() + delta);
        } else {
            redisTemplate.opsForHash().increment(key, hashKey, delta);
        }
    }

    /**
     * Deletes the hash key
     *
     * @param key     blog statistic namespace
     * @param hashKey hash key: blogId
     */
    public void delete(@NonNull String key, @NonNull String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * Deletes the key
     *
     * @param key blog statistic namespace
     */
    public void delete(@NonNull String key) {
        redisTemplate.delete(key);
    }

    /**
     * 手动实现Cacheable
     *
     * @param namespace cache namespace
     * @param key       key
     * @param supplier  gets a result from db
     * @param clazz     type of the returned value
     * @param <T>       class type
     * @return the cached value
     */
    public <T> T cacheable(@NonNull String namespace, @NonNull String key, @NonNull Supplier<T> supplier, @NonNull Class<T> clazz) {
        Cache cache = cacheManager.getCache(namespace);
        if (Objects.isNull(cache)) {
            return supplier.get();
        }
        return Optional.ofNullable(cache.get(key, clazz)).orElseGet(() -> {
            T result = supplier.get();
            cache.put(key, result);
            return result;
        });
    }

    /**
     * Evict the mapping for this key from this cache if it is present
     *
     * @param namespace cache namespace
     * @param key       key
     */
    public void evict(@NonNull String namespace, @NonNull String key) {
        Cache cache = cacheManager.getCache(namespace);
        if (cache == null) {
            return;
        }
        cache.evict(key);
    }
}