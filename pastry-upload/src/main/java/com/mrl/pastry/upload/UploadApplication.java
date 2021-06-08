package com.mrl.pastry.upload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Upload application
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/11
 */
@MapperScan("com.mrl.pastry.upload.mapper")
@SpringBootApplication
public class UploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadApplication.class, args);
    }
}
