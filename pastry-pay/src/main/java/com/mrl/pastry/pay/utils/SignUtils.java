package com.mrl.pastry.pay.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.mrl.pastry.pay.model.params.ConfirmItem;
import com.mrl.pastry.pay.model.params.OrderCallback;
import com.mrl.pastry.pay.model.params.OrderSubmitParam;
import com.mrl.pastry.pay.model.vo.CartItemVO;
import com.mrl.pastry.pay.model.vo.CartListVO;
import com.mrl.pastry.pay.model.vo.PaySignature;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Sign utilities
 *
 * @author MrL
 * @version 1.0
 * @link https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=20_1
 * @date 2021/5/11
 */
@Slf4j
public class SignUtils {

    /**
     * 订单确认时用到的MD5签名Key，主要是防止恶意篡改
     */
    private final static String CONFIRM_KEY = "#{Mrl.Pastry.order$confirm}";

    private SignUtils() {
    }

    /**
     * Check Callback signature
     *
     * @param callback must not be null
     * @return true:ok
     */
    public static boolean checkOrderCallback(@NonNull OrderCallback callback, @NonNull String key) {
        Assert.notNull(callback, "callback info must not be null");
        Assert.notNull(key, "key must not be null");

        Map<String, Object> map = new HashMap<>(8);
        map.put("code", callback.getCode());
        map.put("orderNo", callback.getOrderNo());
        map.put("outTradeNo", callback.getOutTradeNo());
        map.put("payNo", callback.getPayNo());
        map.put("money", callback.getMoney());
        map.put("mchId", callback.getMchId());

        String data = sortMap(map) + "&key=" + key;
        String sign = SecureUtil.md5(data).toUpperCase();
        log.debug("order callback: [{}], key: [{}], sign: [{}]", callback, key, sign);
        return sign.equals(callback.getSign());
    }

    /**
     * Set signature
     *
     * @param signature must not be null
     */
    public static void setSignature(@NonNull PaySignature signature, @NonNull String key) {
        Assert.notNull(signature, "signature must not be null");
        Assert.notNull(key, "key must not be null");

        Map<String, Object> map = new HashMap<>(6);
        map.put("out_trade_no", signature.getOut_trade_no());
        map.put("total_fee", signature.getTotal_fee());
        map.put("mch_id", signature.getMch_id());
        map.put("body", signature.getBody());

        String data = sortMap(map) + "&key=" + key;
        String sign = SecureUtil.md5(data).toUpperCase();
        signature.setSign(sign);
        log.debug("signature :[{}], key: [{}]", signature, key);
    }

    private static String sortMap(Map<String, Object> map) {
        return MapUtil.sortJoin(map, "&", "=", true);
    }

    /**
     * Build the signature for order confirmation
     *
     * @param confirm must not be null
     * @param token   order payment token
     * @return signature
     */
    public static String buildSignature(@NonNull CartListVO confirm, @NonNull String token) {
        Assert.notNull(confirm, "order confirmation must not be null");
        Assert.notNull(token, "payment token must not be null");

        Map<String, Object> data = confirm.getList().stream()
                .collect(Collectors.toMap(item -> item.getProduct().getId(), CartItemVO::getCount));
        data.put("price", confirm.getPrice());
        data.put("token", token);
        data.put("key", CONFIRM_KEY);
        return SecureUtil.md5(sortMap(data)).toUpperCase();
    }

    public static boolean checkOrderSignature(@NonNull OrderSubmitParam submit) {
        Assert.notNull(submit, "submitted order information must not be null");

        Map<String, Object> data = submit.getList().stream()
                .collect(Collectors.toMap(item -> item.getProductId().toString(), ConfirmItem::getCount));
        data.put("price", submit.getPrice());
        data.put("token", submit.getOrderToken());
        data.put("key", CONFIRM_KEY);
        String sign = SecureUtil.md5(sortMap(data)).toUpperCase();
        return submit.getSign().equals(sign);
    }
}
