package com.mrl.pastry.gateway.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.List;

/**
 * Swagger handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/15
 */
@RestController
@RequestMapping("/swagger-resources")
@ConditionalOnProperty(value = "springfox.documentation.enabled", havingValue = "true")
public class SwaggerHandler {

    @Autowired
    private SwaggerResourcesProvider swaggerResources;

    @GetMapping
    public Mono<ResponseEntity<List<SwaggerResource>>> swaggerResources() {
        return Mono.just(ResponseEntity.ok(swaggerResources.get()));
    }

    @GetMapping("configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> getSecurityConfiguration() {
        return Mono.just(ResponseEntity.ok(SecurityConfigurationBuilder.builder().build()));
    }

    @GetMapping("configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> getUiConfiguration() {
        return Mono.just(ResponseEntity.ok(UiConfigurationBuilder.builder().build()));
    }
}
