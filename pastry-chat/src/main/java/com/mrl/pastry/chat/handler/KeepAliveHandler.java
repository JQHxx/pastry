package com.mrl.pastry.chat.handler;

import com.mrl.pastry.chat.model.chat.RequestChatEntity;
import io.netty.channel.Channel;
import org.springframework.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * KeepAlive handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/8
 */
@Slf4j
@Component
public class KeepAliveHandler extends AbstractEventHandler {

    @Override
    void validate(@NonNull RequestChatEntity entity) {
        Assert.notNull(entity.getSenderId(), "senderId must not be null");
    }

    @Override
    void doHandle(Channel channel, @NonNull RequestChatEntity entity) {
        // log.debug("heartbeat....");
    }
}
