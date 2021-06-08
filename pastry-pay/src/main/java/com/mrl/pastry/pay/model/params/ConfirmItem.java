package com.mrl.pastry.pay.model.params;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Cart item params
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/17
 */
@Data
public class ConfirmItem {

    @NotNull(message = "商品不存在")
    private Long productId;

    @Min(value = 1, message = "至少添加一件商品")
    private Integer count;
}
