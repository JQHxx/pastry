package com.mrl.pastry.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.pay.mapper.OrderItemMapper;
import com.mrl.pastry.pay.model.entity.OrderItem;
import com.mrl.pastry.pay.model.vo.OrderItemVO;
import com.mrl.pastry.pay.service.OrderItemService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Order item service implementation
 *
 * @author MrL
 * @since 2021-04-18
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Override
    public List<OrderItemVO> getOrderItemVO(@NonNull String orderSn) {
        Assert.notNull(orderSn, "order_sn must not be null");
        // 优化：索引('order_sn')
        return this.baseMapper.getOrderItemVO(orderSn);
    }
}
