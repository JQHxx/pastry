package com.mrl.pastry.common.config;

import io.swagger.models.auth.In;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract swagger configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/16
 */
public abstract class AbstractSwaggerConfiguration {

    protected Docket buildApiDocket(@NonNull String basePackage) {
        // 这里不要配置groupName（保证仅通过[service]/v2/api-docs路径获取api数据）, 否则会影响gateway聚合
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securityScheme())
                .securityContexts(securityContexts());
    }

    /**
     * To customize the api information
     *
     * @return ApiInfo
     */
    public abstract ApiInfo apiInfo();

    private List<SecurityScheme> securityScheme() {
        return Collections.singletonList(new ApiKey("Authorization", HttpHeaders.AUTHORIZATION, In.HEADER.name()));
    }

    /**
     * To customize the security context
     *
     * @return a list of SecurityContext
     */
    public abstract List<SecurityContext> securityContexts();

    protected List<SecurityContext> setSecurityContexts(String... pathRegex) {
        return Arrays.stream(pathRegex).map(regex -> SecurityContext.builder()
                .securityReferences(securityReferenceList())
                .forPaths(PathSelectors.regex(regex)).build())
                .collect(Collectors.toList());
    }

    private List<SecurityReference> securityReferenceList() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "access everything")};
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }
}
