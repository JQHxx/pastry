package com.mrl.pastry.pay.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Order confirm vo
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/10
 */
@Data
@AllArgsConstructor
public class OrderConfirmVO {

    CartListVO items;

    String orderToken;

    String sign;
}
