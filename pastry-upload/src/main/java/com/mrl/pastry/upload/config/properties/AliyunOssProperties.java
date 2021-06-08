package com.mrl.pastry.upload.config.properties;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.mrl.pastry.upload.handler.file.AliyunOssFileHandler;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Aliyun oss configuration properties
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/8
 */
@Data
@Configuration
@ConditionalOnClass(AliyunOssFileHandler.class)
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssProperties {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    @Bean
    public OSS client() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}