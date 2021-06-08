package com.mrl.pastry.common.exception;

import org.springframework.http.HttpStatus;

/**
 * UnAuthorized Exception
 *
 * @author MrL
 * @version 1.0
 * @date 2021/6/7
 */
public class UnAuthorizedException extends AbstractRuntimeException {

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
