package com.mrl.pastry.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.pastry.pay.model.entity.OrderItem;
import com.mrl.pastry.pay.model.vo.OrderItemVO;

import java.util.List;

/**
 * Order item service
 *
 * @author MrL
 * @since 2021-04-18
 */
public interface OrderItemService extends IService<OrderItem> {

    /**
     * Gets order item vo by order_sn
     *
     * @param orderSn must not be null
     * @return OrderItemVO
     */
    List<OrderItemVO> getOrderItemVO(String orderSn);
}
