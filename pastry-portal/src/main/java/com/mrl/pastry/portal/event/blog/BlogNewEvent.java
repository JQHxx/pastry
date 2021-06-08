package com.mrl.pastry.portal.event.blog;

import com.mrl.pastry.portal.model.entity.Blog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Blog new event
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/27
 */
public class BlogNewEvent extends ApplicationEvent {

    @Getter
    private final Blog blog;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public BlogNewEvent(Object source, Blog blog) {
        super(source);
        this.blog = blog;
    }
}
