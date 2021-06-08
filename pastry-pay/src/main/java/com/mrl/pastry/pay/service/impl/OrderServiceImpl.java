package com.mrl.pastry.pay.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mr.pastry.common.handler.RabbitSender;
import com.mrl.pastry.common.exception.BadRequestException;
import com.mrl.pastry.common.utils.BeanUtils;
import com.mrl.pastry.common.utils.PageUtils;
import com.mrl.pastry.common.utils.SecurityUtils;
import com.mrl.pastry.pay.config.properties.PayProperties;
import com.mrl.pastry.pay.constant.RedisConstant;
import com.mrl.pastry.pay.exception.OrderException;
import com.mrl.pastry.pay.exception.UnderStockException;
import com.mrl.pastry.pay.mapper.OrderMapper;
import com.mrl.pastry.pay.model.entity.OrderInfo;
import com.mrl.pastry.pay.model.entity.OrderItem;
import com.mrl.pastry.pay.model.entity.Product;
import com.mrl.pastry.pay.model.enums.OrderStatus;
import com.mrl.pastry.pay.model.params.ConfirmItem;
import com.mrl.pastry.pay.model.params.OrderCallback;
import com.mrl.pastry.pay.model.params.OrderSubmitParam;
import com.mrl.pastry.pay.model.support.LockWrapper;
import com.mrl.pastry.pay.model.vo.CartListVO;
import com.mrl.pastry.pay.model.vo.OrderConfirmVO;
import com.mrl.pastry.pay.model.vo.OrderListVO;
import com.mrl.pastry.pay.model.vo.PaySignature;
import com.mrl.pastry.pay.service.CartService;
import com.mrl.pastry.pay.service.OrderItemService;
import com.mrl.pastry.pay.service.OrderService;
import com.mrl.pastry.pay.service.ProductService;
import com.mrl.pastry.pay.utils.SignUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mrl.pastry.pay.constant.RabbitConstant.*;

/**
 * Order service implementation
 *
 * @author MrL
 * @since 2021-04-16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderInfo> implements OrderService {

    private final OrderItemService orderItemService;

    private final ProductService productService;

    private final CartService cartService;

    private final PayProperties payProperties;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private final RedissonClient redissonClient;

    private final RabbitSender rabbitSender;

    @Override
    public OrderConfirmVO confirm() {
        // TODO: 积分、优惠券等module待开发

        // get checked items
        CartListVO cartList = cartService.getCartList(true);
        if (CollectionUtils.isEmpty(cartList.getList())) {
            throw new BadRequestException("请先选择商品!");
        }
        // set payment token
        Long userId = SecurityUtils.getCurrentUserId();
        // TODO: 暂时用分布式Id
        String token = IdWorker.getIdStr();
        redisTemplate.opsForValue().set(RedisConstant.ORDER_TOKEN + userId, token);

        // set order signature
        String sign = SignUtils.buildSignature(cartList, token);
        return new OrderConfirmVO(cartList, token, sign);
    }

    @Override
    public PaySignature pay(@NonNull OrderSubmitParam submitParam) {
        Assert.notNull(submitParam, "submitted order data must not be null");
        log.debug("submitted order: [{}]", submitParam);

        if (!SignUtils.checkOrderSignature(submitParam)) {
            throw new OrderException("订单信息已过期");
        }

        Long userId = SecurityUtils.getCurrentUserId();
        RLock lock = redissonClient.getLock("pay");
        lock.lock();
        try {
            String orderToken = submitParam.getOrderToken();
            String token = (String) redisTemplate.opsForValue().get(RedisConstant.ORDER_TOKEN + userId);
            if (StringUtils.isEmpty(token) || !token.equals(orderToken)) {
                log.error("orderToken: [{}], current token: [{}] in redis", orderToken, token);
                throw new OrderException("订单信息已过期!");
            }
            redisTemplate.delete(RedisConstant.ORDER_TOKEN + userId);

            // Get the submitted items
            List<ConfirmItem> items = submitParam.getList();

            // recheck total price 签名包含price信息，可以忽略这步
            /*BigDecimal totalPrice = items.stream().map(item -> {
                BigDecimal productPrice = productService.getProductPrice(item.getProductId());
                return BigDecimal.valueOf(item.getCount()).multiply(productPrice);
            }).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_DOWN);
            if (totalPrice.compareTo(submitParam.getPrice()) != 0) {
                log.error("order totalPrice:[{}], rechecked totalPrice:[{}] from db", submitParam.getPrice(), totalPrice);
                throw new OrderException("订单信息已过期，请重新下单");
            }*/

            // lock the inventory
            List<LockWrapper> lockWrappers = items.stream().map(item ->
                    new LockWrapper(item.getProductId(), item.getCount())).collect(Collectors.toList());
            List<Long> unlocked = productService.lockInventory(lockWrappers);
            if (!CollectionUtils.isEmpty(unlocked)) {
                log.error("unlocked products: [{}]", unlocked);
                throw new UnderStockException("库存不足!").setError(unlocked);
            }

            try {
                OrderInfo order = new OrderInfo();
                // 暂时这么设置
                order.setOrderSn(orderToken);

                int totalCoin = 0;
                for (ConfirmItem item : items) {
                    Product product = this.productService.getOne(Wrappers.<Product>lambdaQuery()
                            .eq(Product::getId, item.getProductId()));
                    // set order item info
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderSn(order.getOrderSn());
                    orderItem.setProductId(product.getId());
                    orderItem.setTitle(product.getTitle());
                    orderItem.setSubtitle(product.getSubtitle());
                    orderItem.setThumbnail(product.getThumbnail());
                    orderItem.setPrice(product.getPrice());
                    orderItem.setCount(item.getCount());
                    orderItem.setGiftCoin(product.getGiftCoin());
                    orderItem.setTotalPrice(orderItem.getPrice().multiply(new BigDecimal(orderItem.getCount())));
                    // save order item
                    orderItemService.save(orderItem);
                    log.debug("saved an orderItem: [{}]", orderItem);

                    totalCoin += orderItem.getCount() * orderItem.getGiftCoin();
                }
                // set order info
                order.setUserId(userId);
                order.setPayType(submitParam.getPayType());
                order.setStatus(OrderStatus.UNPAID);
                order.setTotalPrice(submitParam.getPrice());
                order.setTotalCoin(totalCoin);
                order.setDescription("pastry-pay");
                // save order
                this.save(order);
                log.debug("saved an order: [{}]", order);

                // send delay message
                rabbitSender.send(ORDER_DEAD_EXCHANGE, ORDER_TTL_ROUTING, MapUtil.of("orderSn", order.getOrderSn()));

                // delete checked items
                cartService.deleteChecked(items.stream().map(ConfirmItem::getProductId).collect(Collectors.toList()));

                // build PaySignature
                return buildPaySignature(submitParam.getPrice().toString(), order.getOrderSn());
            } catch (Exception e) {
                log.error(e.getMessage());
                // 下单失败，解锁库存 & 抛出异常rollback
                productService.unlockInventory(lockWrappers);
                throw e;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public PaySignature pay(@NonNull String orderSn) {
        Assert.notNull(orderSn, "orderSn must not be null");
        Long userId = SecurityUtils.getCurrentUserId();
        OrderInfo order = getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getUserId, userId)
                .eq(OrderInfo::getOrderSn, orderSn).select(OrderInfo::getTotalPrice, OrderInfo::getStatus));
        Objects.requireNonNull(order, "订单不存在");
        if (order.getStatus() == OrderStatus.MANUAL_CANCELED) {
            throw new OrderException("订单已取消!");
        } else if (order.getStatus() == OrderStatus.AUTO_CANCELED) {
            throw new OrderException("订单已超时!");
        } else if (order.getStatus() == OrderStatus.PAID) {
            throw new OrderException("订单已支付!");
        }
        return buildPaySignature(order.getTotalPrice().toString(), orderSn);
    }

    @Override
    public IPage<OrderListVO> getOrderList(Pageable pageable, Long limit) {
        Long userId = SecurityUtils.getCurrentUserId();
        Page<OrderInfo> page = PageUtils.convertToPage(pageable);
        // 优化：索引（user_id,id）
        LambdaQueryWrapper<OrderInfo> queryWrapper = Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getUserId, userId);
        if (!Objects.isNull(limit)) {
            // query the previous page
            queryWrapper.lt(OrderInfo::getId, limit);
        }
        return page(page, queryWrapper).convert(order -> {
            OrderListVO orderListVO = BeanUtils.copyProperties(order, OrderListVO.class);
            orderListVO.setItems(orderItemService.getOrderItemVO(order.getOrderSn()));
            return orderListVO;
        });
    }

    @Override
    public PaySignature buildPaySignature(@NonNull String fee, @NonNull String orderSn) {
        Assert.notNull(fee, "price must not be null");
        Assert.notNull(fee, "trade_no must not be null");

        PaySignature signature = new PaySignature();
        signature.setOut_trade_no(orderSn);
        signature.setTotal_fee(fee);
        signature.setMch_id(payProperties.getMch_id());
        signature.setBody("");
        signature.setNotify_url(payProperties.getNotify_url());
        signature.setTitle("酥饼小程序");
        // set signature
        SignUtils.setSignature(signature, payProperties.getKey());
        log.debug("payment signature: [{}]", signature);
        return signature;
    }

    @Override
    public boolean checkCallback(@NonNull OrderCallback orderCallback) {
        Assert.notNull(orderCallback, "callback info must not be null");
        return SignUtils.checkOrderCallback(orderCallback, payProperties.getKey());
    }

    @Override
    public String process(@NonNull OrderCallback orderCallback) {
        Assert.notNull(orderCallback, "callback info must not be null");
        log.debug("start process order:[{}]", orderCallback);

        // 签名错误，可能被篡改
        if (!checkCallback(orderCallback)) {
            log.debug("error signature : [{}]", orderCallback.getSign());
            return "FAILED";
        }

        // 支付失败，订单状态维持UNPAID，等待用户发起支付 或 自动取消
        if (orderCallback.getCode() == 0) {
            log.debug("unable to pay : [{}]", orderCallback.getCode());
            return "SUCCESS";
        }

        RLock payLock = redissonClient.getLock("process");
        payLock.lock();
        try {
            String outTradeNo = orderCallback.getOutTradeNo();
            OrderInfo order = getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getOrderSn, outTradeNo)
                    .select(OrderInfo::getId, OrderInfo::getOrderSn, OrderInfo::getUserId, OrderInfo::getTotalCoin, OrderInfo::getStatus));

            // 已支付
            if (order.getStatus() == OrderStatus.PAID) {
                return "SUCCESS";
            }

            // 更新订单状态, 只要是未支付都改为支付状态，场景（callback在mq取消订单之后到达）也是不影响的
            boolean result = update(Wrappers.<OrderInfo>lambdaUpdate().eq(OrderInfo::getId, order.getId())
                    .set(OrderInfo::getStatus, OrderStatus.PAID)
                    .set(OrderInfo::getOrderNo, orderCallback.getOrderNo())
                    .set(OrderInfo::getPayNo, orderCallback.getPayNo())
                    .set(OrderInfo::getPayTime, orderCallback.getTime()));
            log.debug("order callback : update order:[{}], result:[{}]", order, result);

            // 减库存
            decreaseInventory(order.getOrderSn());

            // 异步更新用户coin
            Map<String, Object> data = new HashMap<>(4);
            data.put("id", order.getUserId());
            data.put("coin", order.getTotalCoin());
            rabbitSender.send(USER_COIN_EXCHANGE, USER_COIN_ROUTING, data);
            return "SUCCESS";
        } finally {
            payLock.unlock();
        }
    }

    @Override
    public void cancelOrder(@NonNull String orderSn, Boolean auto) {
        Assert.notNull(orderSn, "order_sn must not be null");
        OrderInfo order = getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getOrderSn, orderSn)
                .select(OrderInfo::getId, OrderInfo::getStatus));
        if (order.getStatus() == OrderStatus.UNPAID) {
            boolean result = update(Wrappers.<OrderInfo>lambdaUpdate().eq(OrderInfo::getId, order.getId())
                    .set(OrderInfo::getStatus, auto ? OrderStatus.AUTO_CANCELED : OrderStatus.MANUAL_CANCELED));
            log.debug("cancel order : data [{}], result [{}]", order, result);
            // 解锁库存
            unlockInventory(orderSn);
        } else if (order.getStatus() == OrderStatus.AUTO_CANCELED || order.getStatus() == OrderStatus.MANUAL_CANCELED) {
            throw new OrderException("请勿重复取消订单!");
        } else {
            throw new OrderException("已支付订单不支持取消!");
        }
    }

    @Override
    public void decreaseInventory(@NonNull String orderSn) {
        Assert.notNull(orderSn, "order_sn must not be null");
        List<OrderItem> list = orderItemService.list(Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderSn, orderSn)
                .select(OrderItem::getProductId, OrderItem::getCount));
        list.forEach(item -> productService.decreaseInventory(item.getProductId(), item.getCount()));
        log.debug("decrease inventory : order_sn [{}], items [{}]", orderSn, list);
    }

    @Override
    public void unlockInventory(@NonNull String orderSn) {
        Assert.notNull(orderSn, "order_sn must not be null");
        List<OrderItem> list = orderItemService.list(Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderSn, orderSn)
                .select(OrderItem::getProductId, OrderItem::getCount));
        list.forEach(item -> productService.unlockInventory(item.getProductId(), item.getCount()));
        log.debug("unlock inventory : order_sn [{}], items [{}]", orderSn, list);
    }
}
