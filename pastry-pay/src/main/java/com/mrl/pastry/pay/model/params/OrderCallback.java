package com.mrl.pastry.pay.model.params;

import lombok.Data;

/**
 * Order callback
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/11
 */
@Data
public class OrderCallback {

    /**
     * 支付结果 1/0
     */
    private Integer code;

    /**
     * YunGouOS系统内单号
     */
    private String orderNo;

    /**
     * order_sn
     */
    private String outTradeNo;

    /**
     * 第三方支付单号
     */
    private String payNo;

    /**
     * 支付金额
     */
    private String money;

    /**
     * 商户号
     */
    private String mchId;

    // 以上参数参与签名 //

    /**
     * 支付渠道 wxpay/alipay
     */
    private String payChannel;

    /**
     * 支付时间
     */
    private String time;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 用户的openId
     */
    private String openId;

    /**
     * 签名
     */
    private String sign;
}
