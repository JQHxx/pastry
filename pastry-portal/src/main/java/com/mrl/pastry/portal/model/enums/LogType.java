package com.mrl.pastry.portal.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * Log type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/28
 */
public enum LogType implements EnumConverter<Integer> {

    /**
     * register
     */
    REGISTER(0),

    /**
     * refresh token
     */
    REFRESH_TOKEN(1),

    /**
     * profile updated
     */
    PROFILE_UPDATED(10),

    /**
     * avatar updated
     */
    AVATAR_UPDATED(11),

    /**
     * blog published
     */
    BLOG_PUBLISHED(20),

    /**
     * blog deleted
     */
    BLOG_DELETED(21);

    @EnumValue
    private final Integer index;

    LogType(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getValue() {
        return index;
    }
}
