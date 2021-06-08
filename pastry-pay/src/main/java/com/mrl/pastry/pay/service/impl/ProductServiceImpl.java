package com.mrl.pastry.pay.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.common.utils.PageUtils;
import com.mrl.pastry.pay.constant.RedisConstant;
import com.mrl.pastry.pay.mapper.ProductMapper;
import com.mrl.pastry.pay.model.entity.Product;
import com.mrl.pastry.pay.model.enums.ProductType;
import com.mrl.pastry.pay.model.support.LockWrapper;
import com.mrl.pastry.pay.model.vo.ProductVO;
import com.mrl.pastry.pay.service.ProductService;
import com.mrl.pastry.pay.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Product service implementation
 *
 * @author MrL
 * @since 2021-04-16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    private final RedissonClient redissonClient;
    private final RedisUtils redisUtils;

    @Override
    public IPage<ProductVO> getProductPage(ProductType type, Pageable pageable) {
        Page<Product> page = PageUtils.convertToPage(pageable);
        // 优化：索引（type,priority）
        page(page, Wrappers.<Product>lambdaQuery().eq(Product::getType, type).select(Product::getId));
        return page.convert(product -> getProductVO(product.getId()));
    }

    @Override
    public BigDecimal getProductPrice(@NonNull Long id) {
        Assert.notNull(id, "product id must not be null");
        return this.baseMapper.getProductPrice(id);
    }

    @Override
    public ProductVO getProductVO(@NonNull Long id) {
        Assert.notNull(id, "product id must not be null");
        return redisUtils.cacheable(RedisConstant.PRODUCT, id.toString(),
                () -> this.baseMapper.getProductVO(id), ProductVO.class);
        // return this.baseMapper.getProductVO(id);
    }

    @Override
    public List<Long> lockInventory(List<LockWrapper> list) {
        // try lock
        list.forEach(this::tryLockInventory);
        // get the products that failed to lock
        List<LockWrapper> unlocked = list.stream().filter(info -> !info.getLocked()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(unlocked)) {
            // unlock the locked inventory
            List<LockWrapper> locked = list.stream().filter(LockWrapper::getLocked).collect(Collectors.toList());
            locked.forEach(info -> unlockInventory(info.getProductId(), info.getCount()));
            // return unlocked productIds
            return unlocked.stream().map(LockWrapper::getProductId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void decreaseInventory(@NonNull Long productId, @NonNull Integer count) {
        this.baseMapper.decrease(productId, count);
    }

    @Override
    public void unlockInventory(List<LockWrapper> list) {
        list.forEach(info -> unlockInventory(info.getProductId(), info.getCount()));
    }

    @Override
    public void unlockInventory(@NonNull Long productId, @NonNull Integer count) {
        this.baseMapper.unlock(productId, count);
        log.debug("unlock inventory: product[{}] count[{}]", productId, count);
    }

    @Override
    public void tryLockInventory(LockWrapper info) {
        Long productId = info.getProductId();
        RLock lock = redissonClient.getLock(RedisConstant.PRODUCT_STORE_LOCK + productId);
        lock.lock();
        try {
            int success = this.baseMapper.tryLock(productId, info.getCount());
            info.setLocked(success == 1);
        } finally {
            lock.unlock();
        }
    }
}
