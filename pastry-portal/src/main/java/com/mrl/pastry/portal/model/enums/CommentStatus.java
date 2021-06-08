package com.mrl.pastry.portal.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * @author MrL
 * @version 1.0
 * @date 2021/3/16
 */
public enum CommentStatus implements EnumConverter<Integer> {

    /**
     * Published
     */
    PUBLISHED(0),

    /**
     * Auditing
     */
    AUDITING(1),

    /**
     * Abandon
     */
    RECYCLE(2);

    @EnumValue
    private final Integer index;

    CommentStatus(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getValue() {
        return index;
    }
}
