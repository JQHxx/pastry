package com.mrl.pastry.common.support;

import com.mrl.pastry.common.exception.ServiceException;
import org.springframework.lang.NonNull;

import java.util.stream.Stream;

/**
 * Enumeration converter
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/8
 */
public interface EnumConverter<V> {

    /**
     * Converts value to enum
     *
     * @param value    value of type T
     * @param enumType enum class of type E
     * @param <T>      value type
     * @param <E>      enum type
     * @return matched enum
     */
    static <T, E extends EnumConverter<T>> E valueToEnum(@NonNull T value, @NonNull Class<E> enumType) {
        return Stream.of(enumType.getEnumConstants())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new ServiceException("mismatched value"));
    }

    /**
     * Get enum value
     *
     * @return enum value
     */
    V getValue();
}
