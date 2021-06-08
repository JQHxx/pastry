package com.mrl.pastry.portal.model.enums;

import com.mrl.pastry.common.support.EnumConverter;
import lombok.Getter;

/**
 * Like status
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/2
 */
@Getter
public enum LikeStatus implements EnumConverter<Integer> {

    /**
     * count -1
     */
    DB_EXIST_DO_CANCEL(1, 1, false),

    /**
     * count 0
     */
    DB_EXIST_DO_LIKE(6, 0, true),

    /**
     * count +1
     */
    DB_NOT_EXIST_DO_LIKE(2, -1, true),

    /**
     * count -1
     */
    DB_NOT_EXIST_DO_CANCEL(5, 1, false);

    private final Integer value;

    private final Integer increase;

    private final Boolean like;

    LikeStatus(Integer value, Integer increase, Boolean like) {
        this.value = value;
        this.increase = increase;
        this.like = like;
    }
}
