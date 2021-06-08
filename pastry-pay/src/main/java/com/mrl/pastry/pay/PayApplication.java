package com.mrl.pastry.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Pay application
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/16
 */
@MapperScan("com.mrl.pastry.pay.mapper")
@SpringBootApplication
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
