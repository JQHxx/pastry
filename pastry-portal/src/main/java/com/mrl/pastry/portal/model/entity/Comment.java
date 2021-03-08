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
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "blog id")
    private Integer blogId;

    @ApiModelProperty(value = "user who comments")
    private Integer userId;

    @ApiModelProperty(value = "parent comment id")
    private Integer parentId;

    @ApiModelProperty(value = "comment content")
    private String content;

    @ApiModelProperty(value = "comment type: admin's, notice, ")
    private Integer type;

    @ApiModelProperty(value = "comment status: published, auditing, recycle")
    private String status;

    @ApiModelProperty(value = "ip address")
    private String ipAddress;

    @ApiModelProperty(value = "user agent")
    private String userAgent;

    @ApiModelProperty(value = "top priority")
    private Integer priority;

    @ApiModelProperty(value = "create time")
    private Date createTime;

    @ApiModelProperty(value = "update time")
    private Date updateTime;


}
