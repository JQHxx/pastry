package com.mrl.pastry.portal.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * Config type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/8
 */
public enum ConfigType implements EnumConverter<Integer> {

    /**
     * System config
     */
    SYSTEM(0),

    /**
     * Custom config
     */
    CUSTOM(1);

    @EnumValue
    private final Integer type;

    ConfigType(Integer type) {
        this.type = type;
    }

    @Override
    public Integer getValue() {
        return type;
    }
}
