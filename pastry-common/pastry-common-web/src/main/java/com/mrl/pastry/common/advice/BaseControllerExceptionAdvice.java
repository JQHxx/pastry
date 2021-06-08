package com.mrl.pastry.common.advice;

import com.mrl.pastry.common.api.PastryStatus;
import com.mrl.pastry.common.api.ResponseEntity;
import com.mrl.pastry.common.exception.AbstractRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Base controller exception handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/14
 */
@Slf4j
public class BaseControllerExceptionAdvice {

    protected <T> ResponseEntity<T> buildExceptionResponse(Throwable t) {
        log.error("caught exception: ", t);
        ResponseEntity<T> response = new ResponseEntity<>();
        response.setMsg(t.getMessage());
        return response;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        ResponseEntity<?> response = buildExceptionResponse(e);
        response.setCode(PastryStatus.NOT_FOUND.getCode());
        return response;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(Exception e) {
        BindingResult result;
        if (e instanceof BindException) {
            result = ((BindException) e).getBindingResult();
        } else {
            result = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        ResponseEntity<Map<String, String>> response = buildExceptionResponse(e);
        response.setCode(PastryStatus.INVALID_PARAM.getCode());
        response.setMsg(result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(",")));
        return response;
    }

    @ExceptionHandler(AbstractRuntimeException.class)
    public org.springframework.http.ResponseEntity<?> handleRuntimeException(AbstractRuntimeException e) {
        ResponseEntity<Object> response = buildExceptionResponse(e);
        response.setCode(e.getStatus().value());
        response.setData(e.getError());
        return new org.springframework.http.ResponseEntity<>(response, e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleGlobalException(Exception e) {
        ResponseEntity<?> response = buildExceptionResponse(e);
        response.setCode(PastryStatus.SERVER_ERROR.getCode());
        return response;
    }
}
