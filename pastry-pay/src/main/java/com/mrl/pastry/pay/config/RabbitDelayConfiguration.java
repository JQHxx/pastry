package com.mrl.pastry.pay.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.mrl.pastry.pay.constant.RabbitConstant.*;

/**
 * Rabbit delay queue configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/18
 */
@Configuration
public class RabbitDelayConfiguration {

    /**
     * ttl_queue_expired_msg -> dead_exchange -(dead_routing_key)> dead_queue
     *
     * @return queue
     */
    @Bean
    public Queue delayQueue() {
        Map<String, Object> arguments = new HashMap<>(6);
        arguments.put(DEAD_LETTER_EXCHANGE, ORDER_DEAD_EXCHANGE);
        arguments.put(DEAD_LETTER_ROUTING_KEY, ORDER_DEAD_ROUTING);
        arguments.put(MESSAGE_TTL, TTL);
        return new Queue(ORDER_TTL_QUEUE, true, false, false, arguments);
    }

    /**
     * new msg -> dead_exchange -(ttl_routing_key)> ttl_queue
     *
     * @return Binding
     */
    @Bean
    public Binding ttlBinding() {
        return new Binding(ORDER_TTL_QUEUE, Binding.DestinationType.QUEUE, ORDER_DEAD_EXCHANGE, ORDER_TTL_ROUTING, null);
    }

    /**
     * The dead_exchange is bound to two queues(ttl_queue、dead_queue)
     *
     * @return Exchange
     */
    @Bean
    public Exchange deadExchange() {
        return ExchangeBuilder.directExchange(ORDER_DEAD_EXCHANGE).durable(true).build();
    }

    /**
     * dead_queue
     *
     * @return Queue
     */
    @Bean
    public Queue expiredQueue() {
        return new Queue(ORDER_DEAD_QUEUE, true, false, false);
    }

    /**
     * expired msg -> dead_exchange -(dead_routing_key)> dead_queue
     *
     * @return Binding
     */
    @Bean
    public Binding expiredBinding() {
        return new Binding(ORDER_DEAD_QUEUE, Binding.DestinationType.QUEUE, ORDER_DEAD_EXCHANGE, ORDER_DEAD_ROUTING, null);
    }
}
