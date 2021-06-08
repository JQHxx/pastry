package com.mrl.pastry.pay.model.params;

import com.mrl.pastry.pay.model.enums.PayType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Pay params
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/18
 */
@Data
public class OrderSubmitParam {

    PayType payType = PayType.WEIXIN;

    @NotNull(message = "订单信息已过期")
    String sign;

    @NotNull(message = "订单信息已过期")
    String orderToken;

    @NotNull(message = "订单信息已过期")
    List<ConfirmItem> list;

    @NotNull(message = "订单信息已过期")
    BigDecimal price;
}
