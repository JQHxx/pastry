package com.mrl.pastry.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.pastry.pay.model.entity.OrderItem;
import com.mrl.pastry.pay.model.vo.OrderItemVO;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Order item mapper
 *
 * @author MrL
 * @since 2021-04-18
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    /**
     * Gets order item vo by order_sn
     *
     * @param orderSn must not be null
     * @return OrderItemVO
     */
    List<OrderItemVO> getOrderItemVO(@NonNull String orderSn);
}
