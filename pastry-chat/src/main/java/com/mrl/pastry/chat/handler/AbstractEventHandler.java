package com.mrl.pastry.chat.handler;

import com.mrl.pastry.chat.model.chat.RequestChatEntity;
import io.netty.channel.Channel;
import org.springframework.lang.NonNull;

/**
 * Event handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/8
 */
public abstract class AbstractEventHandler {

    /**
     * Validate fields
     *
     * @param entity request chat-entity
     */
    abstract void validate(@NonNull RequestChatEntity entity);

    public void handle(@NonNull Channel channel, @NonNull RequestChatEntity entity) {
        validate(entity);
        doHandle(channel, entity);
    }

    /**
     * Handle event
     *
     * @param channel user channel
     * @param entity  event chat entity
     */
    abstract void doHandle(Channel channel, @NonNull RequestChatEntity entity);
}
