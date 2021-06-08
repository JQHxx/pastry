package com.mrl.pastry.portal.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mrl.pastry.portal.model.enums.TargetType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * User like entity
 *
 * @author MrL
 * @since 2021-04-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserLike对象", description = "")
public class UserLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "user id")
    private Long userId;

    @ApiModelProperty(value = "target id")
    private Long targetId;

    @ApiModelProperty(value = "target type: blog, comment, user...")
    private TargetType targetType;

    @ApiModelProperty(value = "like(1),dislike(0)")
    private Boolean status;

    @ApiModelProperty(value = "create_time")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "update_time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public UserLike(Long userId, Long targetId, TargetType targetType, Boolean status) {
        this.userId = userId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.status = status;
    }
}
