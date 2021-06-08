package com.mrl.pastry.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.mrl.pastry.common.exception.ServiceException;
import org.springframework.lang.NonNull;
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
     * @param target target class must not be null
     * @param <T>    target class type
     * @return null if source is null,or instance with non-null properties from source object
     */
    public static <T> T copyProperties(@NonNull Object source, @NonNull Class<T> target) {
        Assert.notNull(target, "target class cannot be null");
        try {
            T targetInstance = target.newInstance();
            copyProperties(source, targetInstance);
            return targetInstance;
        } catch (Exception e) {
            throw new ServiceException("Can not call newInstance() on " + target);
        }
    }

    /**
     * Copy non-null properties from source object to target object
     *
     * @param source source object must not be null
     * @param target target object must not be null
     */
    public static void copyProperties(@NonNull Object source, @NonNull Object target) {
        BeanUtil.copyProperties(source, target, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
    }
}
