package com.mrl.pastry.portal.listener;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mrl.pastry.portal.event.comment.CommentDeleteEvent;
import com.mrl.pastry.portal.event.comment.CommentNewEvent;
import com.mrl.pastry.portal.mapper.BlogMapper;
import com.mrl.pastry.portal.mapper.CommentMapper;
import com.mrl.pastry.portal.model.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Comment event listener
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/31
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final BlogMapper blogMapper;
    private final CommentMapper commentMapper;

    @Async
    @EventListener
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(CommentNewEvent event) {
        // 优化：冗余代替count(*)
        Comment comment = event.getComment();
        if (event.getType()) {
            // 更新博客 comment_count
            blogMapper.increaseCommentCount(comment.getParentId(), 1);
        } else {
            // 更新父级评论 comment_count
            commentMapper.increaseCommentCount(comment.getParentId(), 1);
            // 更新博客 comment_count
            Long blogId = commentMapper.getParentId(comment.getParentId());
            blogMapper.increaseCommentCount(blogId, 1);
        }
        // TODO: 通知用户
    }

    @Async
    @EventListener
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(CommentDeleteEvent event) {
        Comment comment = event.getComment();
        // 获取父级评论
        Optional<Comment> optional = Optional.ofNullable(commentMapper.selectOne(Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getId, comment.getParentId()).select(Comment::getParentId)));
        if (optional.isPresent()) {
            // 更新父级评论 comment_count
            commentMapper.increaseCommentCount(comment.getParentId(), -1);
            // 更新博客 comment_count
            blogMapper.increaseCommentCount(optional.get().getParentId(), -1);
        } else {
            // 更新博客 comment_count
            blogMapper.increaseCommentCount(comment.getParentId(), -1 - comment.getCommentCount());
            // 删除子评论
            commentMapper.delete(Wrappers.<Comment>lambdaQuery().eq(Comment::getParentId, comment.getId()));
        }
    }
}
