package com.mrl.pastry.chat.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * Message status
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/5
 */
public enum Status {

    /**
     * Unsigned
     */
    UNSIGNED(0),

    /**
     * signed
     */
    SIGNED(1);

    @EnumValue
    private final Integer index;

    Status(Integer index) {
        this.index = index;
    }
}
