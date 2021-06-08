package com.mrl.pastry.upload.advice;

import com.mrl.pastry.common.advice.BaseControllerExceptionAdvice;
import com.mrl.pastry.common.api.PastryStatus;
import com.mrl.pastry.common.api.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * ControllerExceptionAdvice
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/12
 */
@RestControllerAdvice("com.mrl.pastry.upload.controller")
public class ControllerExceptionAdvice extends BaseControllerExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUploadSizeExceededException(MaxUploadSizeExceededException e) {
        ResponseEntity<Object> response = buildExceptionResponse(e);
        response.setCode(PastryStatus.BAD_REQUEST.getCode());
        response.setMsg("当前请求超出最大限制：" + e.getMaxUploadSize() + " bytes");
        return response;
    }
}
