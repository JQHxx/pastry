package com.mrl.pastry.pay.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * Order status
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/16
 */
public enum OrderStatus implements EnumConverter<Integer> {

    /**
     * unpaid
     */
    UNPAID(0),

    /**
     * paid
     */
    PAID(1),

    /**
     * 超时取消
     */
    AUTO_CANCELED(2),

    /**
     * 手动取消
     */
    MANUAL_CANCELED(3);

    @EnumValue
    private final Integer index;

    OrderStatus(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getValue() {
        return index;
    }
}
