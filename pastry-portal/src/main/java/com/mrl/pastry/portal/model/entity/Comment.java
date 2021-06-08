package com.mrl.pastry.portal.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mrl.pastry.portal.model.enums.CommentStatus;
import com.mrl.pastry.portal.model.enums.CommentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Comment entity
 *
 * @author MrL
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "parent comment / blog id")
    private Long parentId;

    @ApiModelProperty(value = "receiver id")
    private Long receiverId;

    @ApiModelProperty(value = "replier id")
    private Long replierId;

    @ApiModelProperty(value = "comment content")
    private String content;

    @ApiModelProperty(value = "like count")
    private Integer likeCount;

    @ApiModelProperty(value = "like count")
    private Integer commentCount;

    @ApiModelProperty(value = "top priority")
    private Integer priority;

    @ApiModelProperty(value = "comment type: user, admin...")
    private CommentType type;

    @ApiModelProperty(value = "comment status: published, auditing, recycle")
    private CommentStatus status;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "create time")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "update time")
    private Date updateTime;

    @TableLogic
    @ApiModelProperty(value = "deleted flag")
    private Boolean deleted;
}
