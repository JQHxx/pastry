package com.mrl.pastry.pay.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mrl.pastry.pay.model.enums.OrderStatus;
import com.mrl.pastry.pay.model.enums.PayType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Order list vo
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/13
 */
@Data
public class OrderListVO {

    private String id;

    private String orderSn;

    private String orderNo;

    private String payNo;

    private PayType payType;

    private OrderStatus status;

    private BigDecimal totalPrice;

    private Integer totalCoin;

    private Date payTime;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    private List<OrderItemVO> items;
}
