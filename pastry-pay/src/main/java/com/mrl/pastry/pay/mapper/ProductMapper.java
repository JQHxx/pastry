package com.mrl.pastry.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.pastry.pay.model.entity.Product;
import com.mrl.pastry.pay.model.vo.ProductVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/**
 * Product mapper
 *
 * @author MrL
 * @since 2021-04-16
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * Gets product vo by id
     *
     * @param id must not be null
     * @return ProductVO
     */
    ProductVO getProductVO(@NonNull Long id);

    /**
     * Gets product price
     *
     * @param id must not be null
     * @return price of product
     */
    @Select("select price from product where id = #{id}")
    BigDecimal getProductPrice(@NonNull @Param("id") Long id);

    /**
     * Lock the inventory
     *
     * @param productId must not be null
     * @param count     > 0
     * @return 1: lock successfully
     */
    @Update("update product set lock_stock = lock_stock + #{count} where id = #{id} and stock - lock_stock >= #{count}")
    int tryLock(@NonNull @Param("id") Long productId, @NonNull @Param("count") Integer count);

    /**
     * Unlock the inventory
     *
     * @param productId must not be null
     * @param count     > 0
     * @return 1: unlock successfully
     */
    @Update("update product set lock_stock = lock_stock - #{count} where id = #{id}")
    int unlock(@NonNull @Param("id") Long productId, @NonNull @Param("count") Integer count);

    /**
     * Decrease the inventory
     *
     * @param productId must not be null
     * @param count     > 0
     * @return 1: decrease successfully
     */
    @Update("update product set lock_stock = lock_stock - #{count}, stock = stock - #{count} where id = #{id}")
    int decrease(@NonNull @Param("id") Long productId, @NonNull @Param("count") Integer count);
}
