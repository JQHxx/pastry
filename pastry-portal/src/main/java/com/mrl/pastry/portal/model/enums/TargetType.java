package com.mrl.pastry.portal.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * @author MrL
 * @version 1.0
 * @date 2021/4/2
 */
public enum TargetType implements EnumConverter<Integer> {

    /**
     * blog
     */
    BLOG(0),

    /**
     * comment
     */
    COMMENT(1),

    /**
     * user
     */
    USER(2);

    @EnumValue
    private final Integer index;

    TargetType(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getValue() {
        return index;
    }
}
