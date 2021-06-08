package com.mrl.pastry.pay.constant;

import java.time.Duration;

/**
 * Rabbit constants
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/18
 */
public interface RabbitConstant {

    String DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";

    String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    String MESSAGE_TTL = "x-message-ttl";

    Long TTL = Duration.ofMinutes(3).getSeconds() * 1000;


    String ORDER_DEAD_EXCHANGE = "order.dead.exchange";

    String ORDER_DEAD_ROUTING = "order.dead.key";

    String ORDER_DEAD_QUEUE = "order.dead.queue";


    String ORDER_TTL_ROUTING = "order.ttl.key";

    String ORDER_TTL_QUEUE = "order.ttl.queue";


    String USER_COIN_EXCHANGE = "user.coin.exchange";

    String USER_COIN_ROUTING = "user.coin.increase";
}
