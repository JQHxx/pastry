package com.mrl.pastry.portal.event.comment;

import com.mrl.pastry.portal.model.entity.Comment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

/**
 * Comment new event
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/31
 */
@Getter
public class CommentNewEvent extends ApplicationEvent {

    private final Comment comment;

    private final Boolean type;

    /**
     * @param source  the object on which the event initially occurred or with
     *                which the event is associated (never {@code null})
     * @param comment must not be null
     * @param type    true:blog；false:comment
     */
    public CommentNewEvent(Object source, @NonNull Comment comment, @NonNull Boolean type) {
        super(source);
        this.comment = comment;
        this.type = type;
    }
}
