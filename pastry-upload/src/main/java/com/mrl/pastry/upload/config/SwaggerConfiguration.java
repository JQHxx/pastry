package com.mrl.pastry.upload.config;

import com.mrl.pastry.common.config.AbstractSwaggerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Swagger configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(value = "springfox.documentation.enabled", havingValue = "true")
public class SwaggerConfiguration extends AbstractSwaggerConfiguration {

    @Bean
    public Docket defaultApi() {
        return buildApiDocket("com.mrl.pastry.upload.controller");
    }

    @Override
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Pastry-Upload API Documentation")
                .description("")
                .version("V1.0")
                .termsOfServiceUrl("")
                .contact(new Contact("MrL", "https://github.com/Ljia-ncu", "1307640502@qq.com"))
                .build();
    }

    @Override
    public List<SecurityContext> securityContexts() {
        return setSecurityContexts("/.*");
    }
}
