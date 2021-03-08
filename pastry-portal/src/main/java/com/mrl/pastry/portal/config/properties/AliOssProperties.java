package com.mrl.pastry.portal.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Aliyun Oss properties
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOssProperties {

}
