package com.mr.pastry.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Rabbit sender
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/12
 */
@Slf4j
public class RabbitSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingKey, Object msg) {
        // broker -> exchange
        rabbitTemplate.setConfirmCallback(this);

        // exchange -> queue
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);

        rabbitTemplate.convertAndSend(exchange, routingKey, msg, processor -> {
            MessageProperties properties = processor.getMessageProperties();
            // persistent
            properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return processor;
        });
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            log.error("failed to send msg, correlationData: [{}], cause: [{}]", correlationData, cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int errCode, String errMsg, String exchange, String routingKey) {
        log.error("failed to send msg: [{}], errCode: [{}], cause: [{}], exchange: [{}], routingKey: [{}]",
                message, errCode, errMsg, exchange, routingKey);
    }
}
