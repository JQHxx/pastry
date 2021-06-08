package com.mrl.pastry.common.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Reflect utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/17
 */
public class ReflectUtils {

    private ReflectUtils() {
    }

    /**
     * Get ParameterizedType
     *
     * @param interfaceType interface type
     * @param impl          interface implementation
     * @return ParameterizedType
     */
    public static ParameterizedType getParameterizedType(@NonNull Class<?> interfaceType, @Nullable Class<?> impl) {
        Assert.notNull(interfaceType, "interface type must not be null");
        Assert.isTrue(interfaceType.isInterface(), "the given type must be an interface");

        if (impl == null) {
            return null;
        }

        ParameterizedType currentType = getParameterizedType(interfaceType, impl.getGenericInterfaces());
        if (currentType != null) {
            return currentType;
        }
        return getParameterizedType(interfaceType, impl.getSuperclass());
    }

    /**
     * Get ParameterizedType
     *
     * @param interfaceType interface type
     * @param types         generic interfaces of interface implementation
     * @return ParameterizedType
     */
    public static ParameterizedType getParameterizedType(@NonNull Class<?> interfaceType, Type... types) {
        Assert.notNull(interfaceType, "interface type must not be null");
        for (Type type : types) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType().equals(interfaceType)) {
                return parameterizedType;
            }
        }
        return null;
    }
}
