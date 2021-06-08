package com.mrl.pastry.pay.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Pay properties
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "pay")
public class PayProperties {

    /**
     * 商户加密key
     */
    private String key;

    private String mch_id;

    private String notify_url;
}
