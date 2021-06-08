package com.mrl.pastry.portal.model.params;

import com.mrl.pastry.common.support.ParamConverter;
import com.mrl.pastry.portal.model.entity.Comment;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Comment params
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/26
 */
@Data
public class CommentParam implements ParamConverter<Comment> {

    @NotNull
    private Long parentId;

    @NotNull
    private Long receiverId;

    @NotBlank(message = "评论不能为空")
    @Size(max = 200, message = "内容长度不能超过 {max}")
    private String content;

    /**
     * true: comment on a blog, false: comment on a comment
     */
    private Boolean type = true;
}
