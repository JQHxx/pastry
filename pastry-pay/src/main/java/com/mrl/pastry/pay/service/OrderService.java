package com.mrl.pastry.pay.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.pastry.pay.model.entity.OrderInfo;
import com.mrl.pastry.pay.model.params.OrderCallback;
import com.mrl.pastry.pay.model.params.OrderSubmitParam;
import com.mrl.pastry.pay.model.vo.OrderConfirmVO;
import com.mrl.pastry.pay.model.vo.OrderListVO;
import com.mrl.pastry.pay.model.vo.PaySignature;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

/**
 * Order service implementation
 *
 * @author MrL
 * @since 2021-04-16
 */
public interface OrderService extends IService<OrderInfo> {

    /**
     * Gets order confirmation
     *
     * @return ConfirmVO
     */
    OrderConfirmVO confirm();

    /**
     * Saves order and gets payment signature
     *
     * @param submitParam must not be null
     * @return PaySignature
     */
    @Transactional(rollbackFor = Exception.class)
    PaySignature pay(@NonNull OrderSubmitParam submitParam);

    /**
     * Gets the payment signature
     *
     * @param orderSn must not be null
     * @return PaySignature
     */
    PaySignature pay(@NonNull String orderSn);

    /**
     * Gets a list of orders
     *
     * @param pageable pagination information
     * @param limit    the minimum blogId of previous page
     * @return a list of OrderListVOs
     */
    IPage<OrderListVO> getOrderList(Pageable pageable, Long limit);

    /**
     * Builds the payment signature
     *
     * @param fee     total price
     * @param tradeNo trade_no
     * @return PaySignature
     */
    PaySignature buildPaySignature(@NonNull String fee, @NonNull String tradeNo);

    /**
     * Checks order callback info
     *
     * @param orderCallback must not be null
     * @return true: ok
     */
    boolean checkCallback(@NonNull OrderCallback orderCallback);

    /**
     * Process the order
     *
     * @param orderCallback must not be null
     * @return SUCCESS / FAILED
     */
    @Transactional(rollbackFor = Exception.class)
    String process(@NonNull OrderCallback orderCallback);

    /**
     * Cancel the order
     *
     * @param orderSn must not be null
     * @param auto    true：AUTO_CANCELED，false: MANUAL_CANCELED
     */
    @Transactional(rollbackFor = Exception.class)
    void cancelOrder(@NonNull String orderSn, Boolean auto);

    /**
     * Decrease inventory
     *
     * @param orderSn must not be null
     */
    @Transactional(rollbackFor = Exception.class)
    void decreaseInventory(@NonNull String orderSn);

    /**
     * Unlock inventory
     *
     * @param orderSn must not be null
     */
    @Transactional(rollbackFor = Exception.class)
    void unlockInventory(@NonNull String orderSn);
}
