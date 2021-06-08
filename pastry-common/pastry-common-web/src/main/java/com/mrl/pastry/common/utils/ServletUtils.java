package com.mrl.pastry.common.utils;

import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

/**
 * Servlet utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/27
 */
public class ServletUtils {

    private ServletUtils() {
    }

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Objects.requireNonNull(requestAttributes, "Current requestAttributes is null");
        return requestAttributes.getRequest();
    }

    public static String getRequestIp() {
        return Optional.ofNullable(ServletUtil.getClientIP(getHttpServletRequest())).orElse("unknown ip address");
    }

    public static String getHeaderIgnoreCase(String header) {
        return ServletUtil.getHeaderIgnoreCase(getHttpServletRequest(), header);
    }

    public static String getUserAgent() {
        return Optional.ofNullable(getHeaderIgnoreCase(HttpHeaders.USER_AGENT)).orElse("unknown user agent");
    }
}
