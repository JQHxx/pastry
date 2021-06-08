package com.mrl.pastry.chat.model.chat;

import com.mrl.pastry.chat.model.enums.EventType;
import lombok.Data;

import java.util.List;

/**
 * Client request chat entity
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/8
 */
@Data
public class RequestChatEntity {

    private Long senderId;

    private Long receiverId;

    /**
     * JsonStr: {content:"...", files:[urls]}
     */
    private String content;

    private EventType event;

    /**
     * unsigned message ids
     */
    private List<Long> unsignedIds;
}
