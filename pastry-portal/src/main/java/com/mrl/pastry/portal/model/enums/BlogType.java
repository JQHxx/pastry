package com.mrl.pastry.portal.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * Blog type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/11
 */
public enum BlogType implements EnumConverter<Integer> {

    /**
     * Blog type
     */
    BLOG(0),

    /**
     * Journal type
     */
    JOURNAL(1),

    /**
     * Notice type
     */
    NOTICE(2);

    @EnumValue
    private final Integer index;

    BlogType(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getValue() {
        return index;
    }
}
