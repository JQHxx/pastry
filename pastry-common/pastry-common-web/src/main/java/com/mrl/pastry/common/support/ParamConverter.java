package com.mrl.pastry.common.support;

import com.mrl.pastry.common.utils.BeanUtils;
import com.mrl.pastry.common.utils.ReflectUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * Param Converter
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/17
 */
public interface ParamConverter<DOMAIN> {

    /**
     * Convert to DOMAIN
     *
     * @return DOMAIN Object
     */
    default DOMAIN convertTo() {
        ParameterizedType type = ReflectUtils.getParameterizedType(ParamConverter.class, this.getClass());
        Objects.requireNonNull(type, "cannot get actual ParameterizedType");
        Class<DOMAIN> domain = (Class<DOMAIN>) type.getActualTypeArguments()[0];
        return BeanUtils.copyProperties(this, domain);
    }
}
