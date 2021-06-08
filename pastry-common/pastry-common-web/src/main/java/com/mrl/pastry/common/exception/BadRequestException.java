package com.mrl.pastry.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Bad request exception
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/9
 */
public class BadRequestException extends AbstractRuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
