package com.mrl.pastry.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.pastry.portal.model.dto.CommentDTO;
import com.mrl.pastry.portal.model.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.NonNull;

/**
 * Comment mapper
 *
 * @author MrL
 * @since 2021-03-07
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * Gets Comment dto by commentId
     *
     * @param commentId comment id
     * @return CommentDTO
     */
    CommentDTO getCommentDtoById(Long commentId);

    /**
     * Increases comment(level 1) count
     *
     * @param commentId must not be null
     * @param increase  must not be null
     */
    @Update("update comment set comment_count = comment_count + #{increase} where id = #{commentId}")
    void increaseCommentCount(@NonNull @Param("commentId") Long commentId, @NonNull @Param("increase") Integer increase);

    /**
     * Gets comment count
     *
     * @param commentId must not be null
     * @return count value
     */
    @Select("select comment_count from comment where id = #{commentId}")
    Integer getCommentCount(@NonNull @Param("commentId") Long commentId);

    /**
     * Gets parentId
     *
     * @param commentId must not be null
     * @return parentId
     */
    @Select("select parent_id from comment where id = #{commentId}")
    Long getParentId(@NonNull @Param("commentId") Long commentId);
}
