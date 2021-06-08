package com.mrl.pastry.portal.event;

import lombok.NonNull;
import org.springframework.context.ApplicationEvent;

/**
 * UserLogEvent
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/8
 */
public class LogEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     * @param target must not be null
     */
    public LogEvent(Object source, @NonNull Object target) {
        super(source);
    }
}
