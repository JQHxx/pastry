package com.mrl.pastry.common.api;

import com.sun.istack.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.mrl.pastry.common.api.PastryStatus.SUCCESS;

/**
 * Response entity
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@Data
@AllArgsConstructor
public class ResponseEntity<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResponseEntity() {
    }

    public static <T> ResponseEntity<T> set(@Nullable T data, String message, Integer code) {
        return new ResponseEntity<>(code, message, data);
    }

    public static <T> ResponseEntity<T> set(@Nullable T data, PastryStatus code) {
        return set(data, code.getMsg(), code.getCode());
    }

    public static <T> ResponseEntity<T> set(PastryStatus status) {
        return set(null, status);
    }

    public static <T> ResponseEntity<T> ok(@Nullable T data) {
        return set(data, SUCCESS);
    }

    public static <T> ResponseEntity<T> ok(@Nullable T data, @Nullable String message) {
        return set(data, message, SUCCESS.getCode());
    }

    public static <T> ResponseEntity<T> ok(@Nullable String message) {
        return ok(null, message);
    }
}
