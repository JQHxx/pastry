package com.mrl.pastry.portal.model.enums;


import com.mrl.pastry.common.support.EnumConverter;

/**
 * Cache type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/3
 */
public enum CacheType implements EnumConverter<Integer> {

    /**
     * String
     */
    STRING(0),

    /**
     * Hash
     */
    HASH(1),

    /**
     * Set
     */
    SET(2),

    /**
     * List
     */
    LIST(3),

    /**
     * Zset
     */
    ZSET(4);

    private final Integer index;

    CacheType(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getValue() {
        return index;
    }
}
