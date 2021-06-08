package com.mrl.pastry.pay.model.vo;

import lombok.Data;

/**
 * Pay signature
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/18
 */
@Data
public class PaySignature {

    private String out_trade_no;

    private String total_fee;

    private String mch_id;

    private String body;

    private String notify_url;

    private String sign;

    private String title;
}
