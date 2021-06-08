package com.mrl.pastry.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.pastry.chat.model.chat.ResponseChatEntity;
import com.mrl.pastry.chat.model.entity.Message;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Message service
 *
 * @author MrL
 * @since 2021-04-05
 */
public interface MessageService extends IService<Message> {

    /**
     * Save message
     *
     * @param message EventChatEntity
     */
    void saveMessage(@NonNull Message message);

    /**
     * Convert Message to ResponseChatEntity
     *
     * @param message message
     * @return ResponseChatEntity
     */
    ResponseChatEntity convertToChatEntity(Message message);

    /**
     * Fetch unsigned messages
     *
     * @param userId userId must not be null
     * @return list of ResponseChatEntity
     */
    List<ResponseChatEntity> fetchUnSignedMessages(@NonNull Long userId);

    /**
     * Batch sign
     *
     * @param unsignedIds unsigned message ids
     * @param userId      userId
     */
    void batchSign(@NonNull List<Long> unsignedIds, @NonNull Long userId);
}
