package com.mrl.pastry.pay.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Product vo
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/11
 */
@Data
public class ProductVO {

    private String id;

    private String title;

    private String subtitle;

    private String thumbnail;

    private BigDecimal price;

    // 折扣、上新... tags
}
