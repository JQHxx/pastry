package com.mrl.pastry.portal.event.user;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

/**
 * UserEditEvent
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/15
 */
@Getter
public class UserFollowEvent extends ApplicationEvent {

    private final Long userId;
    private final Long bloggerId;
    private final Boolean increase;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source    the object on which the event initially occurred or with
     *                  which the event is associated (never {@code null})
     * @param userId    must not be null
     * @param bloggerId must not be null
     * @param increase  must not be null
     */
    public UserFollowEvent(Object source, @NonNull Long userId, @NonNull Long bloggerId, @NonNull Boolean increase) {
        super(source);
        this.userId = userId;
        this.bloggerId = bloggerId;
        this.increase = increase;
    }
}
