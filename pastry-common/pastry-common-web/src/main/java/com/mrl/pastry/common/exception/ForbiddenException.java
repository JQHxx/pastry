package com.mrl.pastry.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Forbidden exception
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/18
 */
public class ForbiddenException extends AbstractRuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable throwable) {
        super(message, throwable);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
