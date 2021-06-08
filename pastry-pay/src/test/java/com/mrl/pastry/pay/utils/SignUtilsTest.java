package com.mrl.pastry.pay.utils;

import com.mrl.pastry.pay.model.params.OrderCallback;
import com.mrl.pastry.pay.model.vo.PaySignature;
import org.junit.Test;

/**
 * SignUtils test
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/11
 * @link https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=20_1
 */
public class SignUtilsTest {

    @Test
    public void test() {
        PaySignature signature = new PaySignature();
        signature.setOut_trade_no("123");
        signature.setTotal_fee("100");
        signature.setMch_id("mrl");
        signature.setBody("pastry");

        SignUtils.setSignature(signature, "123456");
    }

    @Test
    public void check() {
        OrderCallback callback = new OrderCallback();
        callback.setCode(1);
        callback.setOrderNo("123456");
        callback.setOutTradeNo("1307640502");
        callback.setPayNo("123");
        callback.setMoney("1.30");
        callback.setMchId("9527");

        //FCCECCD422F21C6525165A6D053A339B
        SignUtils.checkOrderCallback(callback, "123456");
    }
}
