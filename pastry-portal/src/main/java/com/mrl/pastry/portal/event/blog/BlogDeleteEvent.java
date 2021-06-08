package com.mrl.pastry.portal.event.blog;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.context.ApplicationEvent;

/**
 * Blog delete event
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/27
 */
@Getter
public class BlogDeleteEvent extends ApplicationEvent {

    private final Long blogId;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     * @param blogId must not be null
     */
    public BlogDeleteEvent(Object source, @NonNull Long blogId) {
        super(source);
        this.blogId = blogId;
    }
}
