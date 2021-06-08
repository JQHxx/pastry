package com.mrl.pastry.portal.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mrl.pastry.portal.model.enums.LogType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * User Log
 *
 * @author MrL
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserLog对象", description = "")
public class UserLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "user id")
    private Long userId;

    @ApiModelProperty(value = "log type")
    private LogType type;

    @ApiModelProperty(value = "detail")
    private String detail;

    @ApiModelProperty(value = "ip address")
    private String ipAddress;

    @ApiModelProperty(value = "user agent")
    private String userAgent;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "create time")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "update time")
    private Date updateTime;

    public UserLog(LogType type, String detail) {
        this.type = type;
        this.detail = detail;
    }

    public UserLog(LogType type, String detail, Long userId) {
        this.userId = userId;
        this.type = type;
        this.detail = detail;
    }
}
