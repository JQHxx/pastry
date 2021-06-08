package com.mr.pastry.common.config;

import com.mr.pastry.common.handler.RabbitSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/12
 */
@Slf4j
@Configuration
public class RabbitConfiguration {

    @Bean
    RabbitSender sender() {
        return new RabbitSender();
    }


    @Bean
    MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
