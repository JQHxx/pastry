package com.mrl.pastry.portal.config;

import com.mrl.pastry.portal.config.properties.PastryProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Pastry configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(PastryProperties.class)
public class PastryConfiguration {

    @Autowired
    private PastryProperties pastryProperties;

}
