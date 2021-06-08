package com.mrl.pastry.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

/**
 * Abstract runtime exception
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/9
 */
public abstract class AbstractRuntimeException extends RuntimeException {

    @Getter
    private Object error;

    public AbstractRuntimeException(String message) {
        super(message);
    }

    public AbstractRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Set error data
     *
     * @param error error data
     * @return this
     */
    @NonNull
    public AbstractRuntimeException setError(@NonNull Object error) {
        this.error = error;
        return this;
    }

    /**
     * Http status
     *
     * @return {@link HttpStatus}
     */
    public abstract HttpStatus getStatus();
}
