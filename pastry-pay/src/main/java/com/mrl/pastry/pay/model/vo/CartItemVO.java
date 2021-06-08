package com.mrl.pastry.pay.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Cart item vo
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/17
 */
@Data
@AllArgsConstructor
public class CartItemVO {

    private ProductVO product;

    private Integer count;

    private Boolean check;
}
