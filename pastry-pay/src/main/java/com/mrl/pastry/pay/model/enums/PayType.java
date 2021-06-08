package com.mrl.pastry.pay.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * Pay type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/16
 */
public enum PayType implements EnumConverter<Integer> {

    /**
     * Weixin pay
     */
    WEIXIN(0),

    /**
     * Alipay
     */
    ALIPAY(1);

    @EnumValue
    private final Integer index;

    PayType(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getValue() {
        return null;
    }
}
