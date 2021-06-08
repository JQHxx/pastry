package com.mrl.pastry.gateway.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Gateway white-list properties
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/15
 */
@Configuration
@ConfigurationProperties(prefix = "gateway.ignore")
public class IgnoreProperties {

    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
