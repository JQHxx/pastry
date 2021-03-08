package com.mrl.pastry.portal.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author MrL
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Blog对象", description = "")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "category id")
    private Integer categoryId;

    @ApiModelProperty(value = "blog type: default, notice, article...")
    private Integer type;

    @ApiModelProperty(value = "status: published, recycle...")
    private Integer status;

    @ApiModelProperty(value = "blog url")
    private String url;

    @ApiModelProperty(value = "blog title")
    private String title;

    @ApiModelProperty(value = "unformatted content")
    private String originalContent;

    @ApiModelProperty(value = "rendered content")
    private String formatContent;

    @ApiModelProperty(value = "summary")
    private String summary;

    @ApiModelProperty(value = "cover picture")
    private String coverPic;

    @ApiModelProperty(value = "editor: markdown, richtext...")
    private Integer editorType;

    @ApiModelProperty(value = "keywords")
    private String keyword;

    @ApiModelProperty(value = "whether allow comment")
    private Boolean allowreply;

    @ApiModelProperty(value = "top priority")
    private Integer priority;

    @ApiModelProperty(value = "content words count")
    private Integer wordCount;

    @ApiModelProperty(value = "visits count")
    private Integer visitCount;

    @ApiModelProperty(value = "likes count")
    private Integer likeCount;

    @ApiModelProperty(value = "coin count")
    private Integer coinCount;

    @ApiModelProperty(value = "create time")
    private Date createTime;

    @ApiModelProperty(value = "update time")
    private Date updateTime;


}
