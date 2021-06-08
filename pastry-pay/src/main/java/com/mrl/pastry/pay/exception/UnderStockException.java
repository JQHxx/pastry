package com.mrl.pastry.pay.exception;

import com.mrl.pastry.common.exception.AbstractRuntimeException;
import org.springframework.http.HttpStatus;

/**
 * Under stock exception
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/12
 */
public class UnderStockException extends AbstractRuntimeException {

    public UnderStockException(String message) {
        super(message);
    }

    public UnderStockException(String message, Throwable throwable) {
        super(message, throwable);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
