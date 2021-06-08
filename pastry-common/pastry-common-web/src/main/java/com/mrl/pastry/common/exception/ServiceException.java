package com.mrl.pastry.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Service internal exception
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/9
 */
public class ServiceException extends AbstractRuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
