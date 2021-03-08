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
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "wx openid")
    private Integer openId;

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

    @ApiModelProperty(value = "description")
    private String description;

    @ApiModelProperty(value = "blog count")
    private Integer blogCount;

    @ApiModelProperty(value = "coin_count")
    private Integer coinCount;

    @ApiModelProperty(value = "create time")
    private Date createTime;

    @ApiModelProperty(value = "update time")
    private Date updateTime;


}
