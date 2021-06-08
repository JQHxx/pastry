package com.mrl.pastry.portal.model.vo;

import com.mrl.pastry.portal.model.dto.CommentDTO;
import com.mrl.pastry.portal.model.dto.UserBaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SubComment VO
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SubCommentVO extends CommentDTO {

    private UserBaseDTO receiver;

    private UserBaseDTO replier;
}
