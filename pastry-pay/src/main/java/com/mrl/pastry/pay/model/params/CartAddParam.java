package com.mrl.pastry.pay.model.params;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Cart add params
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/19
 */
@Data
public class CartAddParam {

    @NotNull(message = "商品不能为空")
    private Long productId;

    private boolean add = true;
}
