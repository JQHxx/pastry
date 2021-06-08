package com.mrl.pastry.chat.handler;

import com.mrl.pastry.chat.model.chat.RequestChatEntity;
import com.mrl.pastry.chat.service.MessageService;
import io.netty.channel.Channel;
import org.springframework.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * Sign handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/8
 */
@Slf4j
@Component
public class SignHandler extends AbstractEventHandler {

    private final MessageService messageService;

    public SignHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    void validate(@NonNull RequestChatEntity entity) {
        Assert.notNull(entity.getSenderId(), "senderId must not be null");
        Assert.notNull(entity.getUnsignedIds(), "unsigned ids must not be null");
        Assert.isTrue(!CollectionUtils.isEmpty(entity.getUnsignedIds()),"unsigned ids is empty");
    }

    @Override
    void doHandle(Channel channel, @NonNull RequestChatEntity entity) {
        messageService.batchSign(entity.getUnsignedIds(), entity.getSenderId());
    }
}
