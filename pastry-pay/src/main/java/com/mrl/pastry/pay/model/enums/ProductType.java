package com.mrl.pastry.pay.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * Product type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/16
 */
public enum ProductType implements EnumConverter<Integer> {

    /**
     * Common type
     */
    COMMON(0),

    /**
     * Vip type
     */
    VIP(1);

    @EnumValue
    private final Integer index;

    ProductType(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getValue() {
        return index;
    }
}
