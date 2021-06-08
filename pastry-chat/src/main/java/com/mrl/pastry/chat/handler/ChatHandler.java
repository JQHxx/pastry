package com.mrl.pastry.chat.handler;

import cn.hutool.json.JSONUtil;
import com.mrl.pastry.chat.core.ChannelHandler;
import com.mrl.pastry.chat.core.UserChannelMap;
import com.mrl.pastry.chat.model.chat.RequestChatEntity;
import com.mrl.pastry.chat.model.entity.Message;
import com.mrl.pastry.chat.model.enums.ContentType;
import com.mrl.pastry.chat.model.enums.Status;
import com.mrl.pastry.chat.service.MessageService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collections;

/**
 * Chat handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/8
 */
@Slf4j
@Component
public class ChatHandler extends AbstractEventHandler {

    private final MessageService messageService;

    public ChatHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void validate(@NonNull RequestChatEntity entity) {
        Assert.notNull(entity.getSenderId(), "senderId must not be null");
        Assert.notNull(entity.getReceiverId(), "receiverId must not be null");
        if (StringUtils.isEmpty(entity.getContent()) || entity.getContent().length() > 200) {
            log.error("caught exception message: [{}]", entity);
            throw new IllegalArgumentException("content is illegal");
        }
    }

    @Override
    public void doHandle(Channel channel, @NonNull RequestChatEntity entity) {
        // save in db
        Message message = new Message().setSenderId(entity.getSenderId())
                .setReceiverId(entity.getReceiverId())
                .setContent(entity.getContent())
                .setContentType(ContentType.TEXT)
                .setStatus(Status.UNSIGNED);
        messageService.save(message);
        log.debug("save a message: [{}]", message);

        // get receiver channel
        Channel receiverChannel = UserChannelMap.get(entity.getReceiverId());
        if (receiverChannel != null) {
            Channel found = ChannelHandler.CLIENTS.find(receiverChannel.id());
            // forward if online
            if (found != null) {
                receiverChannel.writeAndFlush(
                        new TextWebSocketFrame(JSONUtil.toJsonStr(
                                Collections.singletonList(messageService.convertToChatEntity(message))
                        ))
                );
            }
        }
    }
}
