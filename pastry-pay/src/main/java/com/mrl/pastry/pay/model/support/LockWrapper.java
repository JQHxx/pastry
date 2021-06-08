package com.mrl.pastry.pay.model.support;

import lombok.Data;

/**
 * Lock wrapper
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/10
 */
@Data
public class LockWrapper {

    private Long productId;

    private Integer count;

    private Boolean locked;

    public LockWrapper(Long id, Integer count) {
        this.productId = id;
        this.count = count;
    }
}
