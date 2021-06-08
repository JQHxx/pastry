package com.mrl.pastry.chat.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mrl.pastry.chat.model.chat.RequestChatEntity;
import com.mrl.pastry.chat.model.enums.EventType;
import com.mrl.pastry.chat.utils.ApplicationContextUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Event Strategy
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/9
 */
@Slf4j
@Component
public class EventStrategy {

    public void handle(@NonNull Channel channel, @NonNull JSONObject jsonObject) {
        Assert.notNull(channel, "channel must not be null");
        Assert.notNull(jsonObject, "json object must not be null");

        RequestChatEntity requestChatEntity = JSONUtil.toBean(jsonObject, RequestChatEntity.class);
        Objects.requireNonNull(requestChatEntity, "chat-entity is null");
        EventType event = requestChatEntity.getEvent();

        Class<? extends AbstractEventHandler> eventHandler = getEventHandler(event);
        AbstractEventHandler handler = ApplicationContextUtils.getBean(eventHandler);
        handler.handle(channel, requestChatEntity);
    }

    private Class<? extends AbstractEventHandler> getEventHandler(@NonNull EventType event) {
        Class<? extends AbstractEventHandler> clazz;
        switch (event) {
            case CONNECT:
                clazz = ConnectHandler.class;
                break;
            case CHAT:
                clazz = ChatHandler.class;
                break;
            case FETCH:
                clazz = FetchHandler.class;
                break;
            case SIGN:
                clazz = SignHandler.class;
                break;
            case KEEPALIVE:
                clazz = KeepAliveHandler.class;
                break;
            default:
                throw new RuntimeException("No matched event handler");
        }
        return clazz;
    }
}
