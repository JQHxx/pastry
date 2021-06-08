package com.mrl.pastry.upload.config;

import com.mrl.pastry.common.exception.ServiceException;
import com.mrl.pastry.common.support.EnumConverter;
import com.mrl.pastry.upload.handler.file.AliyunOssFileHandler;
import com.mrl.pastry.upload.handler.file.FileHandler;
import com.mrl.pastry.upload.handler.file.QiniuyunOssFileHandler;
import com.mrl.pastry.upload.handler.file.TencentCosFileHandler;
import com.mrl.pastry.upload.model.enums.UploadType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Upload configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@Configuration(proxyBeanMethods = false)
public class UploadConfiguration {

    @Value("${upload.type}")
    private String uploadType;

    @Bean
    private FileHandler fileHandler() {
        FileHandler fileHandler;
        UploadType type = EnumConverter.valueToEnum(uploadType, UploadType.class);
        switch (type) {
            case ALIYUN:
                fileHandler = new AliyunOssFileHandler();
                break;
            case QINIUYUN:
                fileHandler = new QiniuyunOssFileHandler();
                break;
            case TENCENTYUN:
                fileHandler = new TencentCosFileHandler();
                break;
            default:
                throw new ServiceException("no matched file handler");
        }
        return fileHandler;
    }
}
