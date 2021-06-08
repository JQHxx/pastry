package com.mrl.pastry.pay.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mrl.pastry.pay.model.enums.OrderStatus;
import com.mrl.pastry.pay.model.enums.PayType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Order entity
 *
 * @author MrL
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrderInfo对象", description = "")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "user id")
    private Long userId;

    @ApiModelProperty(value = "系统内单号")
    private String orderSn;

    @ApiModelProperty(value = "第三方系统单号")
    private String orderNo;

    @ApiModelProperty(value = "第三方支付单号")
    private String payNo;

    @ApiModelProperty(value = "pay type: weixin(0)")
    private PayType payType;

    @ApiModelProperty(value = "order status")
    private OrderStatus status;

    @ApiModelProperty(value = "total price")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "total coin")
    private Integer totalCoin;

    @ApiModelProperty(value = "product_sn json str")
    private String description;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "create time")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "update time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    @ApiModelProperty(value = "deleted flag")
    private Boolean deleted;
}
