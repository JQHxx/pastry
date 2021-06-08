package com.mrl.pastry.upload.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mrl.pastry.common.support.EnumConverter;

/**
 * Attachment upload type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/8
 */
public enum UploadType implements EnumConverter<String> {

    /**
     * Aliyun oss
     */
    ALIYUN(0, "aliyun"),

    /**
     * Qiniuyun oss
     */
    QINIUYUN(1, "qiniuyun"),

    /**
     * Tencent cos
     */
    TENCENTYUN(2, "tencentyun");

    @EnumValue
    private final Integer index;

    private final String description;

    UploadType(Integer type, String name) {
        this.index = type;
        this.description = name;
    }

    @Override
    public String getValue() {
        return description;
    }
}
