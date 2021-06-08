package com.mrl.pastry.pay.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Cart list vo
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/17
 */
@Data
@AllArgsConstructor
public class CartListVO {

    List<CartItemVO> list;

    BigDecimal price;
}
