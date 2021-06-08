package com.mrl.pastry.chat.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.chat.mapper.MessageMapper;
import com.mrl.pastry.chat.model.chat.ResponseChatEntity;
import com.mrl.pastry.chat.model.entity.Message;
import com.mrl.pastry.chat.model.enums.Status;
import com.mrl.pastry.chat.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Message service implementation
 *
 * @author MrL
 * @since 2021-04-05
 */
@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public void saveMessage(@NonNull Message message) {
        this.baseMapper.insert(message);
        log.debug("insert new message:[{}]", message);
    }

    @Override
    public ResponseChatEntity convertToChatEntity(Message message) {
        return new ResponseChatEntity()
                .setSenderId(message.getSenderId().toString())
                .setReceiverId(message.getReceiverId().toString())
                .setMessageId(message.getId().toString())
                .setContent(message.getContent())
                .setDate(DateUtil.format(message.getCreateTime(), "MM-dd HH:mm"));
    }

    @Override
    public List<ResponseChatEntity> fetchUnSignedMessages(@NonNull Long userId) {
        // 优化：索引(receiver_id)
        List<Message> messages = this.baseMapper.selectList(Wrappers.<Message>lambdaQuery()
                .eq(Message::getReceiverId, userId).eq(Message::getStatus, Status.UNSIGNED));
        return messages.stream().map(this::convertToChatEntity).collect(Collectors.toList());
    }

    @Override
    public void batchSign(@NonNull List<Long> unsignedIds, @NonNull Long userId) {
        this.baseMapper.updateBatchStatus(unsignedIds, userId);
        log.debug("update batch messages: [{}],receiver: [{}]", unsignedIds, userId);
    }
}
