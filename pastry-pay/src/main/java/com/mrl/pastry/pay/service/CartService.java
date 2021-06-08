package com.mrl.pastry.pay.service;

import com.mrl.pastry.pay.model.params.CartAddParam;
import com.mrl.pastry.pay.model.vo.CartListVO;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Cart service interface
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/17
 */
public interface CartService {

    int LIMIT = 99;

    /**
     * Gets the cart list
     *
     * @param check false: get all items; true: get checked items
     * @return list of CartItemVO
     */
    CartListVO getCartList(boolean check);

    /**
     * Adds item
     *
     * @param param cart item must not be null
     * @return CartItemVO
     */
    Integer addItem(@NonNull CartAddParam param);

    /**
     * Checks or unchecks the item
     *
     * @param productId must not be null
     * @return true: checked
     */
    boolean checkItem(@NonNull Long productId);

    /**
     * Deletes checked items
     *
     * @param ids item id
     */
    void deleteChecked(@NonNull List<Long> ids);
}
