package com.mrl.pastry.portal.event.comment;

import com.mrl.pastry.portal.model.entity.Comment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

/**
 * Comment delete event
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/29
 */
public class CommentDeleteEvent extends ApplicationEvent {

    @Getter
    private final Comment comment;

    /**
     * @param source  the object on which the event initially occurred or with
     *                which the event is associated (never {@code null})
     * @param comment must not be null
     */
    public CommentDeleteEvent(Object source, @NonNull Comment comment) {
        super(source);
        this.comment = comment;
    }
}
