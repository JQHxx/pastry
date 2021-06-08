package com.mrl.pastry.portal.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mrl.pastry.portal.model.enums.BlogStatus;
import com.mrl.pastry.portal.model.enums.BlogType;
import com.mrl.pastry.portal.model.enums.EditorType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Blog entity
 *
 * @author MrL
 * @since 2021-03-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Blog对象", description = "")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "user id")
    private Long author;

    @ApiModelProperty(value = "blog type: default, notice, article...")
    private BlogType type;

    @ApiModelProperty(value = "status: published, recycle...")
    private BlogStatus status;

    @ApiModelProperty(value = "blog forward url")
    private String forward;

    @ApiModelProperty(value = "blog title")
    private String title;

    @ApiModelProperty(value = "content")
    private String content;

    @ApiModelProperty(value = "summary")
    private String summary;

    @ApiModelProperty(value = "attachment json str")
    private String attachments;

    @ApiModelProperty(value = "editor: markdown, rich text...")
    private EditorType editorType;

    @ApiModelProperty(value = "true: allow comment")
    private Boolean allowComment;

    @ApiModelProperty(value = "top priority")
    private Integer priority;

    @ApiModelProperty(value = "content words count")
    private Integer wordCount;

    @ApiModelProperty(value = "visits count")
    private Integer visitCount;

    @ApiModelProperty(value = "likes count")
    private Integer likeCount;

    @ApiModelProperty(value = "coins count")
    private Integer coinCount;

    @ApiModelProperty(value = "comments count")
    private Integer commentCount;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "create time")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "update time")
    private Date updateTime;

    @TableLogic
    @ApiModelProperty(value = "deleted flag")
    private Boolean deleted;

    public Blog(Long id, Long author) {
        this.id = id;
        this.author = author;
    }
}
