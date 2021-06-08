package com.mrl.pastry.common.config;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvc configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/8
 */
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 已解决: String类型返回值不走ControllerResultAdvice
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }
}
