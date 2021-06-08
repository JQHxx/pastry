package com.mrl.pastry.portal.event.blog;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

/**
 * BlogCoinEvent
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/7
 */
public class BlogCoinEvent extends ApplicationEvent {

    @Getter
    private final Long blogId;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public BlogCoinEvent(Object source, @NonNull Long blogId) {
        super(source);
        this.blogId = blogId;
    }
}
