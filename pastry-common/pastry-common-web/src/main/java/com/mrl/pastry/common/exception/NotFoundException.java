package com.mrl.pastry.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Not found exception
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/9
 */
public class NotFoundException extends AbstractRuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
