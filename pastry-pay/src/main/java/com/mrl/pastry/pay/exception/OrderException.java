package com.mrl.pastry.pay.exception;

import com.mrl.pastry.common.exception.AbstractRuntimeException;
import org.springframework.http.HttpStatus;

/**
 * Order exception
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/10
 */
public class OrderException extends AbstractRuntimeException {

    public OrderException(String message) {
        super(message);
    }

    public OrderException(String message, Throwable throwable) {
        super(message, throwable);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
