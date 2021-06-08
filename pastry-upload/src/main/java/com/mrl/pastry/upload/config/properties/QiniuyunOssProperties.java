package com.mrl.pastry.upload.config.properties;

import com.mrl.pastry.upload.handler.file.QiniuyunOssFileHandler;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Qiniuyun oss configuration properties
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/8
 */
@Data
@Configuration
@ConditionalOnClass(QiniuyunOssFileHandler.class)
@ConfigurationProperties(prefix = "qiniuyun.oss")
public class QiniuyunOssProperties {

}
