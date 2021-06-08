package com.mrl.pastry.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Pastry chat application
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/31
 */
@MapperScan("com.mrl.pastry.chat.mapper")
@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}
