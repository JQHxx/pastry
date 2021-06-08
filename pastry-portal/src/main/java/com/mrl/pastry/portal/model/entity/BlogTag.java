package com.mrl.pastry.portal.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Blog Tag
 *
 * @author MrL
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BlogTag对象", description = "")
public class BlogTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "blog id")
    private Long blogId;

    @ApiModelProperty(value = "tag id")
    private Long tagId;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "create time")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "update_time")
    private Date updateTime;
}
