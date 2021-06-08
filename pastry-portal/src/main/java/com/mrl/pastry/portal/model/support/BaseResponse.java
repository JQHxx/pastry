package com.mrl.pastry.portal.model.support;

import com.sun.istack.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Base response entity
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> BaseResponse<T> ok(@Nullable T data, @Nullable String message) {
        return new BaseResponse<>(HttpStatus.OK.value(), message, data);
    }

    public static <T> BaseResponse<T> ok(@Nullable String message) {
        return new BaseResponse<>(HttpStatus.OK.value(), message, null);
    }

    public static <T> BaseResponse<T> ok(@Nullable T data) {
        return new BaseResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

}
