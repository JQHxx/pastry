package com.mrl.pastry.portal.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;
import lombok.Getter;

/**
 * Blog status
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/11
 */
public enum BlogStatus implements EnumConverter<Integer> {

    /**
     * Published status
     */
    PUBLISHED(0),

    /**
     * Draft status
     */
    DRAFT(1),

    /**
     * Recycle status
     */
    RECYCLE(2),

    /**
     * Audit status
     */
    AUDIT(3);

    @Getter
    @EnumValue
    private final Integer value;

    BlogStatus(int value) {
        this.value = value;
    }
}
