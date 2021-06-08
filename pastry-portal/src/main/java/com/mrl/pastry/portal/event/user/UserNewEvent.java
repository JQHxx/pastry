package com.mrl.pastry.portal.event.user;

import com.mrl.pastry.portal.model.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * UserIndexEvent
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/27
 */
public class UserNewEvent extends ApplicationEvent {

    @Getter
    private final User user;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserNewEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
