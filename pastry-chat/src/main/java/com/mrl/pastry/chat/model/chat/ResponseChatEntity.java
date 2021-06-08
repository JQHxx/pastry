package com.mrl.pastry.chat.model.chat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Server response chat entity
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/9
 */
@Data
@Accessors(chain = true)
public class ResponseChatEntity {

    private String senderId;

    private String receiverId;

    private String messageId;

    private String content;

    private String date;
}
