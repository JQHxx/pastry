package com.mrl.pastry.common.fallback;

import com.mrl.pastry.common.api.PastryStatus;
import com.mrl.pastry.common.api.ResponseEntity;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Common feign fallback
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/26
 */
@Slf4j
@AllArgsConstructor
public class PastryFeignFallback<T> implements MethodInterceptor {

    private final Class<T> target;
    private final String targetName;
    private final Throwable cause;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String exception = cause.getMessage();
        log.error("feign fallback: {}.{}, service: {}, cause: {}", target, method.getName(), targetName, exception);
        if (cause instanceof FeignException) {
            exception = ((FeignException) cause).contentUTF8();
        }
        return ResponseEntity.set(exception, PastryStatus.BAD_REQUEST);
    }
}
