package com.mrl.pastry.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.common.exception.BadRequestException;
import com.mrl.pastry.common.utils.BeanUtils;
import com.mrl.pastry.common.utils.PageUtils;
import com.mrl.pastry.common.utils.SecurityUtils;
import com.mrl.pastry.portal.event.comment.CommentDeleteEvent;
import com.mrl.pastry.portal.event.comment.CommentNewEvent;
import com.mrl.pastry.portal.mapper.CommentMapper;
import com.mrl.pastry.portal.model.dto.CommentDTO;
import com.mrl.pastry.portal.model.entity.Blog;
import com.mrl.pastry.portal.model.entity.Comment;
import com.mrl.pastry.portal.model.entity.User;
import com.mrl.pastry.portal.model.enums.CommentStatus;
import com.mrl.pastry.portal.model.enums.CommentType;
import com.mrl.pastry.portal.model.params.CommentParam;
import com.mrl.pastry.portal.model.vo.CommentVO;
import com.mrl.pastry.portal.model.vo.SubCommentVO;
import com.mrl.pastry.portal.service.BlogService;
import com.mrl.pastry.portal.service.CommentService;
import com.mrl.pastry.portal.service.UserService;
import com.mrl.pastry.portal.utils.SensitiveUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

/**
 * Comment service implementation
 *
 * @author MrL
 * @since 2021-03-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    private final SensitiveUtils sensitiveUtils;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public IPage<CommentDTO> getCommentPage(Pageable pageable, Long limit, @NonNull Long parentId, Boolean queryChild) {
        Assert.notNull(parentId, "parentId must not be null");
        Page<Comment> page = PageUtils.convertToPage(pageable);
        // 优化：索引(parentId)
        // query the latest page
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.<Comment>lambdaQuery().eq(Comment::getParentId, parentId);
        if (!Objects.isNull(limit) && limit != 0) {
            // query the previous page
            queryWrapper.lt(Comment::getId, limit);
        }
        return page(page, queryWrapper).convert(queryChild ? this::convertToSubCommentVO : this::convertToCommentVO);
    }

    @Override
    public CommentDTO comment(@NonNull CommentParam param) {
        Assert.notNull(param, "comment param must not be null");
        checkParam(param);

        Long userId = SecurityUtils.getCurrentUserId();
        Comment comment = param.convertTo();
        comment.setReplierId(userId);
        // exclude sensitive words
        String content = sensitiveUtils.refine(comment.getContent());
        comment.setContent(content);
        comment.setType(CommentType.USER);
        // TODO: admin module 审核
        comment.setStatus(CommentStatus.PUBLISHED);
        save(comment);
        log.debug("user: [{}] post a comment: [{}]", userId, comment);

        // publish comment-new event
        eventPublisher.publishEvent(new CommentNewEvent(this, comment, param.getType()));
        return param.getType() ? this.convertToCommentVO(comment) : this.convertToSubCommentVO(comment);
    }

    private void checkParam(CommentParam param) {
        Long parentId = param.getParentId();
        if (param.getType()) {
            Optional.ofNullable(blogService.getOne(Wrappers.<Blog>lambdaQuery().eq(Blog::getId, parentId).select(Blog::getId)))
                    .orElseThrow(() -> new BadRequestException("回复主体不存在!"));
        } else {
            Optional.ofNullable(getOne(Wrappers.<Comment>lambdaQuery().eq(Comment::getId, parentId).select(Comment::getId)))
                    .orElseThrow(() -> new BadRequestException("回复主体不存在!"));
        }
        userService.getOneUserByQueryWrapper(Wrappers.<User>lambdaQuery().eq(User::getId, param.getReceiverId()).select(User::getId))
                .orElseThrow(() -> new BadRequestException("回复对象不存在!"));
    }

    @Override
    public CommentVO convertToCommentVO(@NonNull Comment comment) {
        CommentVO commentVO = BeanUtils.copyProperties(comment, CommentVO.class);
        commentVO.setReplier(userService.getUserDto(comment.getReplierId()));
        return commentVO;
    }

    @Override
    public SubCommentVO convertToSubCommentVO(@NonNull Comment comment) {
        SubCommentVO commentChildVO = BeanUtils.copyProperties(comment, SubCommentVO.class);
        commentChildVO.setReceiver(userService.getUserDto(comment.getReceiverId()));
        commentChildVO.setReplier(userService.getUserDto(comment.getReplierId()));
        return commentChildVO;
    }

    @Override
    public void deleteComment(@NonNull Long commentId) {
        Long userId = SecurityUtils.getCurrentUserId();
        // id、parentId、commentCount 方便做异步处理
        Comment comment = getOne(Wrappers.<Comment>lambdaQuery().eq(Comment::getId, commentId).eq(Comment::getReplierId, userId)
                .select(Comment::getId, Comment::getParentId, Comment::getCommentCount));
        if (!Objects.isNull(comment)) {
            boolean deleted = remove(Wrappers.<Comment>lambdaQuery().eq(Comment::getId, commentId));
            log.debug("delete a comment:[{}] result:[{}]", commentId, deleted);
            if (deleted) {
                // publish comment-delete event
                eventPublisher.publishEvent(new CommentDeleteEvent(this, comment));
            } else {
                throw new BadRequestException("删除评论失败!");
            }
        } else {
            throw new BadRequestException("删除评论失败!");
        }
    }
}