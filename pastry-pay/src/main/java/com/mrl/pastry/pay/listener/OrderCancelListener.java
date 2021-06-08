package com.mrl.pastry.pay.listener;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mrl.pastry.pay.constant.RabbitConstant;
import com.mrl.pastry.pay.exception.OrderException;
import com.mrl.pastry.pay.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Order cancel listener
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/16
 */
@Slf4j
@Component
public class OrderCancelListener {

    private final OrderService orderService;

    public OrderCancelListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = {RabbitConstant.ORDER_DEAD_QUEUE})
    public void listener(Message msg, Channel channel) throws IOException {
        JSONObject json = JSONUtil.parseObj(new String(msg.getBody()));
        try {
            // 超时自动取消
            orderService.cancelOrder(json.getStr("orderSn"), true);
        } catch (OrderException e) {
            // 重复取消订单异常
            log.error("message: [{}], exception: [{}]", json, e.getMessage());
        }
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
