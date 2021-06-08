package com.mrl.pastry.common;

import com.mrl.pastry.common.advice.ControllerLogAdvice;
import com.mrl.pastry.common.config.GlobalCorsConfiguration;
import com.mrl.pastry.common.config.WebConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web auto configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/11
 */
@Configuration
public class WebAutoConfigure {

    @Bean
    public WebConfiguration webConfiguration() {
        return new WebConfiguration();
    }

    @Bean
    public ControllerLogAdvice controllerLogAdvice() {
        return new ControllerLogAdvice();
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalCorsConfiguration globalCorsConfiguration() {
        return new GlobalCorsConfiguration();
    }
}
