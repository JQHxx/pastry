package com.mrl.pastry.portal.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Weixin auth parameters
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@Data
public class WxAuthParam {

    @NotBlank(message = "code must not be blank")
    private String code;

    @NotBlank(message = "encryptedData must not be blank")
    private String encryptedData;

    @NotBlank(message = "iv cannot be blank")
    private String iv;
}
