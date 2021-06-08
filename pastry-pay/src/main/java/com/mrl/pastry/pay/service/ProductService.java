package com.mrl.pastry.pay.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.pastry.pay.model.entity.Product;
import com.mrl.pastry.pay.model.enums.ProductType;
import com.mrl.pastry.pay.model.support.LockWrapper;
import com.mrl.pastry.pay.model.vo.ProductVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Product service
 *
 * @author MrL
 * @since 2021-04-16
 */
public interface ProductService extends IService<Product> {

    /**
     * Get a page of products
     *
     * @param type     product type
     * @param pageable pagination information
     * @return a page of productVO
     */
    IPage<ProductVO> getProductPage(@Nullable ProductType type, Pageable pageable);

    /**
     * Gets product price
     *
     * @param id must not be null
     * @return price of product
     */
    BigDecimal getProductPrice(@NonNull @Param("id") Long id);

    /**
     * Get product dto by sn
     *
     * @param id must not be null
     * @return ProductDTO
     */
    ProductVO getProductVO(@NonNull Long id);

    /**
     * Locks inventory
     *
     * @param list list of lock info
     * @return list of unlocked productIds
     */
    @Transactional(rollbackFor = Exception.class)
    List<Long> lockInventory(List<LockWrapper> list);

    /**
     * Try lock inventory
     *
     * @param info lock info
     */
    @Transactional(rollbackFor = Exception.class)
    void tryLockInventory(LockWrapper info);

    /**
     * Locks inventory
     *
     * @param list list of lock-info
     */
    @Transactional(rollbackFor = Exception.class)
    void unlockInventory(List<LockWrapper> list);

    /**
     * Unlock the inventory
     *
     * @param productId must not be null
     * @param count     >0
     */
    @Transactional(rollbackFor = Exception.class)
    void unlockInventory(@NonNull Long productId, @NonNull Integer count);

    /**
     * Decrease the inventory
     *
     * @param productId must not be null
     * @param count     >0
     */
    @Transactional(rollbackFor = Exception.class)
    void decreaseInventory(@NonNull Long productId, @NonNull Integer count);
}
