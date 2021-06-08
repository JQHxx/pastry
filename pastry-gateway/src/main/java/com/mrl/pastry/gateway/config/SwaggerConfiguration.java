package com.mrl.pastry.gateway.config;

import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Gateway swagger configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/24
 */
@Primary
@Configuration
public class SwaggerConfiguration implements SwaggerResourcesProvider {

    private static final String SWAGGER2URL = "v2/api-docs";

    private final GatewayProperties gatewayProperties;

    private final RouteLocator routeLocator;

    public SwaggerConfiguration(GatewayProperties gatewayProperties, RouteLocator routeLocator) {
        this.gatewayProperties = gatewayProperties;
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));

        List<SwaggerResource> resources = new ArrayList<>();
        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                        .replace("**", SWAGGER2URL)))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
