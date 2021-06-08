package com.mrl.pastry.pay.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mrl.pastry.pay.model.enums.ProductType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Product entity
 *
 * @author MrL
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Product对象", description = "")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "product type: common(0), vip(1)")
    private ProductType type;

    @ApiModelProperty(value = "on(1), off(0)")
    private Boolean status;

    @ApiModelProperty(value = "product title")
    private String title;

    @ApiModelProperty(value = "product subtitle")
    private String subtitle;

    @ApiModelProperty(value = "thumbnail")
    private String thumbnail;

    @ApiModelProperty(value = "price")
    private BigDecimal price;

    @ApiModelProperty(value = "promotion price")
    private BigDecimal promotionPrice;

    @ApiModelProperty(value = "promotion limit")
    private Integer promotionLimit;

    @ApiModelProperty(value = "coins ")
    private Integer giftCoin;

    @ApiModelProperty(value = "priority")
    private String priority;

    @ApiModelProperty(value = "stock")
    private Integer stock;

    @ApiModelProperty(value = "lock_stock")
    private Integer lockStock;

    @ApiModelProperty(value = "sales")
    private Integer sales;

    @ApiModelProperty(value = "create time")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "update time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    @ApiModelProperty(value = "delete flag")
    private Boolean deleted;
}
