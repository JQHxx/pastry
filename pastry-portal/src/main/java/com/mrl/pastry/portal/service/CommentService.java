package com.mrl.pastry.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.pastry.portal.model.dto.CommentDTO;
import com.mrl.pastry.portal.model.entity.Comment;
import com.mrl.pastry.portal.model.params.CommentParam;
import com.mrl.pastry.portal.model.vo.CommentVO;
import com.mrl.pastry.portal.model.vo.SubCommentVO;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

/**
 * Comment service interface
 *
 * @author MrL
 * @since 2021-03-07
 */
public interface CommentService extends IService<Comment> {

    /**
     * Gets a page of comments
     * If query blog comments, parentId is the blogId;
     * If query child comments, parentId is the parent commentId;
     *
     * @param limit      the minimum commentId of previous page
     * @param parentId   the id of parent subject(blog / comment)
     * @param queryChild whether to query child comments
     * @param pageable   pagination information
     * @return a page of commentVO / commentChildVO
     */
    IPage<CommentDTO> getCommentPage(Pageable pageable, Long limit, @NonNull Long parentId, Boolean queryChild);

    /**
     * Post a comment
     * If reply to a blog, parentId is the blogId and receiverId is the authorId;
     * If reply to a comment, parentId is the commentId and the receiverId is the replierId of the comment;
     * If reply to a child comment, parentId is the parent commentId and the receiverId is the replierId of the child comment;
     *
     * @param commentParam must not be null
     * @return CommentVO
     */
    @Transactional(rollbackFor = Exception.class)
    CommentDTO comment(@NonNull CommentParam commentParam);

    /**
     * Convert comment to CommentVO
     *
     * @param comment must not be null
     * @return CommentVO
     */
    CommentVO convertToCommentVO(@NonNull Comment comment);

    /**
     * Convert comment to CommentChildVO
     *
     * @param comment must not be null
     * @return CommentChildVO
     */
    SubCommentVO convertToSubCommentVO(@NonNull Comment comment);

    /**
     * Delete a comment
     *
     * @param commentId must not be null
     */
    void deleteComment(@NonNull Long commentId);
}
