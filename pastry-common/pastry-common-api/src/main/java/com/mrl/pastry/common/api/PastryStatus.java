package com.mrl.pastry.common.api;

import lombok.Getter;

/**
 * Response status
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/26
 */
@Getter
public enum PastryStatus {

    /**
     * Customized status
     */
    SUCCESS(200, "ok"),

    BAD_REQUEST(400, "请求异常"),

    INVALID_PARAM(400, "参数校验失败"),

    UNAUTHORIZED(401, "未授权访问"),

    FORBIDDEN(403, "禁止访问"),

    NOT_FOUND(404, "未发现该服务"),

    TOO_MANY_REQUESTS(429, "系统繁忙"),

    SERVER_ERROR(500, "系统故障");

    private final int code;

    private final String msg;

    PastryStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
