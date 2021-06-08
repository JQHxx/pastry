package com.mrl.pastry.common.advice;

import cn.hutool.json.JSONUtil;
import com.mrl.pastry.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Controller log aspect
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/14
 */
@Slf4j
@Aspect
public class ControllerLogAdvice {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void controller() {
    }

    @Around("restController() || controller()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method == null) {
            return joinPoint.proceed();
        }
        // request
        HttpServletRequest request = ServletUtils.getHttpServletRequest();

        // meta data
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();

        StopWatch stopWatch = new StopWatch(request.getRequestURI());
        stopWatch.start("request");
        printRequestLog(request, className, methodName, args);
        stopWatch.stop();

        stopWatch.start("exec: " + className + "#" + methodName);
        Object result = joinPoint.proceed();
        stopWatch.stop();

        stopWatch.start("response");
        printResponseLog(className, methodName, result);
        stopWatch.stop();

        log.debug("exec:\n [{}]", stopWatch.prettyPrint());
        return result;
    }

    private void printRequestLog(HttpServletRequest request, String className, String methodName, Object[] args) {
        log.debug("Request URL: [{}], URI: [{}], Request Method: [{}], IP: [{}]",
                request.getRequestURL(), request.getRequestURI(), request.getMethod(), ServletUtils.getRequestIp());

        boolean omit = false;
        for (Object arg : args) {
            if (arg instanceof MultipartFile
                    || arg instanceof HttpServletRequest
                    || arg instanceof HttpServletResponse) {
                omit = true;
                break;
            }
        }
        if (!omit) {
            // 记一个StackOverFlow: 如果之前没有排除request，这里执行JSONUtil.toJsonStr直接导致栈溢出
            log.debug("request: {}.{} parameters [{}]", className, methodName, JSONUtil.toJsonStr(args));
        }
    }

    private void printResponseLog(String className, String methodName, Object result) {
        if (result != null) {
            log.debug("response: {}.{} result [{}]", className, methodName, JSONUtil.toJsonStr(result));
        }
    }
}
