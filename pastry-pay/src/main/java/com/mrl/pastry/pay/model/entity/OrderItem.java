package com.mrl.pastry.pay.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Order item
 *
 * @author MrL
 * @since 2021-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrderItem对象", description = "")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "order sn")
    private String orderSn;

    @ApiModelProperty(value = "product id")
    private Long productId;

    @ApiModelProperty(value = "product title")
    private String title;

    @ApiModelProperty(value = "product subtitle")
    private String subtitle;

    @ApiModelProperty(value = "thumbnail")
    private String thumbnail;

    @ApiModelProperty(value = "price")
    private BigDecimal price;

    @ApiModelProperty(value = "gift coin")
    private Integer giftCoin;

    @ApiModelProperty(value = "count")
    private Integer count;

    @ApiModelProperty(value = "total price")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "create time")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "update time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "deleted flag")
    private Boolean deleted;
}
