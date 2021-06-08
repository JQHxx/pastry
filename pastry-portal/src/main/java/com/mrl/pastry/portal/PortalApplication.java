package com.mrl.pastry.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Portal Application
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@MapperScan("com.mrl.pastry.portal.mapper")
@EnableFeignClients(basePackages = "com.mrl.pastry.portal.feign")
@EnableScheduling
@SpringBootApplication
public class PortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }
}
