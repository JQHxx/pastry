package com.mrl.pastry.chat.handler;

import cn.hutool.json.JSONUtil;
import com.mrl.pastry.chat.model.chat.RequestChatEntity;
import com.mrl.pastry.chat.model.chat.ResponseChatEntity;
import com.mrl.pastry.chat.service.MessageService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Fetch handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/8
 */
@Component
public class FetchHandler extends AbstractEventHandler {

    private final MessageService messageService;

    public FetchHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    void validate(@NonNull RequestChatEntity entity) {
        Assert.notNull(entity.getSenderId(), "senderId must not be null");
    }

    @Override
    void doHandle(Channel channel, @NonNull RequestChatEntity entity) {
        List<ResponseChatEntity> messages = messageService.fetchUnSignedMessages(entity.getSenderId());
        if (!CollectionUtils.isEmpty(messages)) {
            channel.writeAndFlush(
                    new TextWebSocketFrame(JSONUtil.toJsonStr(messages)));
        }
    }
}
