package com.mrl.pastry.chat.core;

import io.netty.channel.Channel;
import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentMap;

/**
 * User channel map
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/5
 */
@Slf4j
public class UserChannelMap {

    /**
     * TODO: 考虑扩展
     */
    private static final ConcurrentMap<Long, Channel> GROUP = PlatformDependent.newConcurrentHashMap();

    public static void put(Long userId, Channel channel) {
        GROUP.put(userId, channel);
        log.debug("User Channel Map added new channel: [userId: {}, channel: {}]", userId, channel.id().asLongText());
    }

    public static Channel get(Long userId) {
        return GROUP.get(userId);
    }
}
