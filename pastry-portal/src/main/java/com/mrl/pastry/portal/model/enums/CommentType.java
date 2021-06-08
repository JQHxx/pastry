package com.mrl.pastry.portal.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * Comment type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/16
 */
public enum CommentType implements EnumConverter<Integer> {

    /**
     * User comment
     */
    USER(0),

    /**
     * Blogger comment
     */
    BLOGGER(1),

    /**
     * Admin comment
     */
    ADMIN(2);

    @EnumValue
    private final Integer index;

    CommentType(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getValue() {
        return index;
    }
}
