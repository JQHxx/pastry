package com.mrl.pastry.chat.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * Event type
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/4
 */
public enum EventType {

    /**
     * Connect: add user channel
     */
    CONNECT(0),

    /**
     * Chat: save in DB and forward message
     */
    CHAT(1),

    /**
     * Sign: batch sign
     */
    SIGN(2),

    /**
     * Fetch: fetch unsigned messages
     */
    FETCH(3),

    /**
     * Keep alive: ping/pong
     */
    KEEPALIVE(4);

    @EnumValue
    private final Integer index;

    EventType(Integer index) {
        this.index = index;
    }
}
