package com.mrl.pastry.pay.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Order item vo
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/13
 */
@Data
public class OrderItemVO {

    private String productId;

    private String title;

    private String subtitle;

    private String thumbnail;

    private BigDecimal price;

    private Integer giftCoin;

    private Integer count;

    private BigDecimal totalPrice;
}
