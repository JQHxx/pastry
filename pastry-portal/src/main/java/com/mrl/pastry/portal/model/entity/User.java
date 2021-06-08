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
 * User
 *
 * @author MrL
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "wx openid")
    private String openId;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "nickname")
    private String nickname;

    @ApiModelProperty(value = "phone number")
    private String phone;

    @ApiModelProperty(value = "gender: w(0),m(1)")
    private Boolean gender;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "avatar url")
    private String avatar;

    @ApiModelProperty(value = "profile")
    private String profile;

    @ApiModelProperty(value = "coin_count")
    private Integer coinCount;

    @ApiModelProperty(value = "blog_count")
    private Integer blogCount;

    @ApiModelProperty(value = "follow_count")
    private Integer followCount;

    @ApiModelProperty(value = "fans_count")
    private Integer fansCount;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "create time")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "update time")
    private Date updateTime;
}
