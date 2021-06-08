package com.mrl.pastry.pay.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mrl.pastry.common.exception.BadRequestException;
import com.mrl.pastry.common.utils.SecurityUtils;
import com.mrl.pastry.pay.constant.RedisConstant;
import com.mrl.pastry.pay.model.entity.Product;
import com.mrl.pastry.pay.model.params.CartAddParam;
import com.mrl.pastry.pay.model.vo.CartItemVO;
import com.mrl.pastry.pay.model.vo.CartListVO;
import com.mrl.pastry.pay.model.vo.ProductVO;
import com.mrl.pastry.pay.service.CartService;
import com.mrl.pastry.pay.service.ProductService;
import com.mrl.pastry.pay.utils.CheckUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Cart service implementation
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductService productService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "decreaseItem")
    private DefaultRedisScript<Long> decreaseItem;

    @Resource(name = "checkItem")
    private DefaultRedisScript<Long> checkItem;

    private final RedissonClient redissonClient;

    @Override
    public Integer addItem(@NonNull CartAddParam param) {
        Optional.ofNullable(productService.getOne(Wrappers.<Product>lambdaQuery().eq(Product::getId, param.getProductId()).select(Product::getId)))
                .orElseThrow(() -> new BadRequestException("该商品不存在"));
        String key = RedisConstant.CART + SecurityUtils.getCurrentUserId();
        String hashKey = param.getProductId().toString();
        Long result;
        if (param.isAdd()) {
            result = redisTemplate.opsForHash().increment(key, hashKey, 1);
            redisTemplate.expire(key, Duration.ofDays(1));
        } else {
            result = redisTemplate.execute(decreaseItem, Arrays.asList(key, hashKey));
            Objects.requireNonNull(result);
            if (result == -1) {
                throw new BadRequestException("该商品已删除");
            }
        }
        return CheckUtils.getQuantity(result.intValue());
    }

    @Override
    public boolean checkItem(@NonNull Long productId) {
        String key = RedisConstant.CART + SecurityUtils.getCurrentUserId();
        Long result = redisTemplate.execute(checkItem, Arrays.asList(key, productId.toString()));
        Objects.requireNonNull(result);
        if (result == -1) {
            throw new BadRequestException("您还未添加该商品");
        }
        return result == 1;
        /* 加锁实现
        RLock lock = redissonClient.getLock(userId.toString());
        lock.lock();
        try {
            String key = RedisConstant.CART + userId;
            String hashKey = productId.toString();
            Integer value = (Integer) redisTemplate.opsForHash().get(key, hashKey);
            Objects.requireNonNull(value);
            value = CheckUtils.setChecked(value);
            redisTemplate.opsForHash().put(key, hashKey, value);
            return CheckUtils.check(value);
        } finally {
            lock.unlock();
        }*/
    }

    @Override
    public void deleteChecked(@NonNull List<Long> ids) {
        Long userId = SecurityUtils.getCurrentUserId();
        ids.forEach(id -> redisTemplate.opsForHash().delete(RedisConstant.CART + userId, id.toString()));
    }

    @Override
    public CartListVO getCartList(boolean check) {
        Long userId = SecurityUtils.getCurrentUserId();
        String key = RedisConstant.CART + userId;

        // get all or checked items
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        List<CartItemVO> list = entries.entrySet().stream().map(entry -> {
            String productId = (String) entry.getKey();
            ProductVO product = productService.getProductVO(Long.parseLong(productId));
            Integer count = (Integer) entry.getValue();
            return new CartItemVO(product, CheckUtils.getQuantity(count), CheckUtils.check(count));
        }).filter(item -> !check || item.getCheck()).collect(Collectors.toList());

        // get the total price of checked items
        BigDecimal total = list.stream().filter(CartItemVO::getCheck)
                .map(item -> BigDecimal.valueOf(item.getCount()).multiply(item.getProduct().getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_DOWN);
        return new CartListVO(list, total);
    }
}
