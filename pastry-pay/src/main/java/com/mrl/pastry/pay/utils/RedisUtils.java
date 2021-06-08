package com.mrl.pastry.pay.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Redis utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/11
 */
@Slf4j
@Component
public class RedisUtils {

    private final RedisCacheManager cacheManager;

    public RedisUtils(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Cache the result
     *
     * @param key      cache namespace
     * @param hashKey  hash key
     * @param supplier data from db
     * @param clazz    class
     * @param <T>      class type
     * @return the cached data
     */
    public <T> T cacheable(@NonNull String key, @NonNull String hashKey, Supplier<T> supplier, @NonNull Class<T> clazz) {
        Cache cache = cacheManager.getCache(key);
        if (cache == null) {
            return supplier.get();
        }
        return Optional.ofNullable(cache.get(hashKey, clazz)).orElseGet(() -> {
            T result = supplier.get();
            cache.putIfAbsent(hashKey, result);
            return result;
        });
    }
}
