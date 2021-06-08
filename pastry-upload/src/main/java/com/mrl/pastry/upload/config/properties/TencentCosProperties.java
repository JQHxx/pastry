package com.mrl.pastry.upload.config.properties;

import com.mrl.pastry.upload.handler.file.TencentCosFileHandler;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Tencent cos configuration properties
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/9
 */
@Data
@Configuration
@ConditionalOnClass(TencentCosFileHandler.class)
@ConfigurationProperties(prefix = "tencent.oss")
public class TencentCosProperties {

}
