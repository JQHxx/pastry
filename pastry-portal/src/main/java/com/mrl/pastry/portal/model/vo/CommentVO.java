package com.mrl.pastry.portal.model.vo;

import com.mrl.pastry.portal.model.dto.CommentDTO;
import com.mrl.pastry.portal.model.dto.UserBaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Comment vo
 * 一级评论
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentVO extends CommentDTO {

    private UserBaseDTO replier;

    private Integer commentCount;

    // private Integer likeCount;
}
