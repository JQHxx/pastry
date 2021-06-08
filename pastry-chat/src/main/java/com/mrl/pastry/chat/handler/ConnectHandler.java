package com.mrl.pastry.chat.handler;

import com.mrl.pastry.chat.core.UserChannelMap;
import com.mrl.pastry.chat.model.chat.RequestChatEntity;
import io.netty.channel.Channel;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Connect handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/8
 */
@Component
public class ConnectHandler extends AbstractEventHandler {

    @Override
    public void validate(@NonNull RequestChatEntity entity) {
        Assert.notNull(entity.getSenderId(), "senderId must not be null");
    }

    @Override
    public void doHandle(Channel channel, @NonNull RequestChatEntity entity) {
        UserChannelMap.put(entity.getSenderId(), channel);
    }
}
