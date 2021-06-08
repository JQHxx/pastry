package com.mrl.pastry.common.advice;

import com.mrl.pastry.common.api.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.swagger.web.ApiResourceController;

import java.util.Optional;

/**
 * Controller result advice
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/14
 */
@Slf4j
@ControllerAdvice
public class ControllerResultAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        // ignore the Knife4j
        return !returnType.getDeclaringClass().equals(ApiResourceController.class);
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, @NonNull MediaType contentType, @NonNull MethodParameter returnType,
                                           @NonNull ServerHttpRequest httpRequest, @NonNull ServerHttpResponse httpResponse) {
        // get the returned value
        Object value = bodyContainer.getValue();
        // ignore the swagger data
        if (value instanceof Json) {
            return;
        }
        // 前端异常处理以ResponseEntity#code为主
        if (value instanceof ResponseEntity) {
            ResponseEntity<?> response = (ResponseEntity<?>) value;
            httpResponse.setStatusCode(Optional.ofNullable(HttpStatus.resolve(response.getCode()))
                    .orElse(HttpStatus.OK));
            return;
        }
        // if value is not instance of ResponseEntity, just wrap it
        bodyContainer.setValue(ResponseEntity.ok(value));
        httpResponse.setStatusCode(HttpStatus.OK);
    }
}
