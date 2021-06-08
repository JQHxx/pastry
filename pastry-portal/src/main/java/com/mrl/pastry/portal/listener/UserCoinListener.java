package com.mrl.pastry.portal.listener;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mrl.pastry.portal.constant.RabbitConstant;
import com.mrl.pastry.portal.mapper.UserMapper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * User coin listener
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserCoinListener {

    private final UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConstant.USER_COIN_QUEUE, durable = "true"),
            exchange = @Exchange(value = RabbitConstant.USER_COIN_EXCHANGE),
            key = {RabbitConstant.USER_COIN_ROUTING}
    ))
    public void listener(Message msg, Channel channel) throws IOException {
        JSONObject json = JSONUtil.parseObj(new String(msg.getBody()));
        try {
            Long userId = json.getLong("id");
            Integer coin = json.getInt("coin");
            userMapper.increaseCoinCount(userId, coin);
            log.debug("increased user:[{}] coin:[{}]", userId, coin);
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.error("data: [{}], exception: [{}]", json.toString(), e.getMessage());
            throw e;
        }
    }
}
