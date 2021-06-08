package com.mrl.pastry.portal.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * Blog editor type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/11
 */
public enum EditorType implements EnumConverter<Integer> {

    /**
     * Rich_text editor
     */
    RICHTEXT(0),

    /**
     * Markdown editor
     */
    MARKDOWN(1);

    @EnumValue
    private final Integer index;

    EditorType(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getValue() {
        return index;
    }
}
