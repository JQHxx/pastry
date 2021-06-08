package com.mrl.pastry.portal.event.blog;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

/**
 * Blog visit event
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/14
 */
@Getter
public class BlogVisitEvent extends ApplicationEvent {

    private final Long blogId;
    private final Long userId;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     * @param userId must not be null
     * @param blogId must not be null
     */
    public BlogVisitEvent(Object source, @NonNull Long blogId, @NonNull Long userId) {
        super(source);
        this.blogId = blogId;
        this.userId = userId;
    }
}
