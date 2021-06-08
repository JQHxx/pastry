package com.mrl.pastry.chat.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * Content type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/5
 */
public enum ContentType {

    /**
     * text
     */
    TEXT(0),

    /**
     * file
     */
    FILE(1);

    @EnumValue
    private final Integer index;

    ContentType(Integer index) {
        this.index = index;
    }
}
