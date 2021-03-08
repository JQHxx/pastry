package com.mrl.pastry.portal.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Bean utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
public class BeanUtils {

    private BeanUtils() {
    }

    /**
     * Copy non-null properties from source object
     *
     * @param source source object
     * @param target target class cannot be null
     * @param <T>    target class type
     * @return null if source is null,or instance with non-null properties from source object
     */
    public static <T> T transformFrom(@Nullable Object source, @NonNull Class<T> target) {
        Assert.notNull(target, "target class cannot be null");

        if (source == null) {
            return null;
        }

        try {
            T targetInstance = target.newInstance();
            BeanUtil.copyProperties(source, targetInstance, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            return targetInstance;
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    /**
     * Update non-null properties from source object to target object
     *
     * @param source source object cannot be null
     * @param target target object cannot be null
     */
    public static void updateProperties(@NonNull Object source, @NonNull Object target) {
        Assert.notNull(source, "source object cannot be null");
        Assert.notNull(target, "target object cannot be null");

        BeanUtil.copyProperties(source, target, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
    }
}
