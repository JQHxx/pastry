package com.mrl.pastry.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.mrl.pastry.gateway.property.IgnoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Jwt authentication filter
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/22
 */
@Slf4j
@Component
public class GlobalTokenFilter implements GlobalFilter, Ordered {

    private static final String SWAGGER2URL = "/v2/api-docs";

    @Autowired
    private IgnoreProperties properties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (path.endsWith(SWAGGER2URL)) {
            return chain.filter(exchange);
        }
        if (ignore(path)) {
            return chain.filter(exchange);
        }
        String tokenPrefix = "Bearer";
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(token) || !token.startsWith(tokenPrefix)) {
            ServerHttpResponse response = exchange.getResponse();
            return buildResponse(response, HttpStatus.UNAUTHORIZED, "未授权访问");
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    private boolean ignore(String path) {
        return properties.getUrls().stream().map(url -> url.replace("/**", ""))
                .anyMatch(path::startsWith);
    }

    private Mono<Void> buildResponse(ServerHttpResponse response, HttpStatus status, Object data) {
        ResponseEntity<?> entity = ResponseEntity.status(status.value()).body(data);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(status);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(entity).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
