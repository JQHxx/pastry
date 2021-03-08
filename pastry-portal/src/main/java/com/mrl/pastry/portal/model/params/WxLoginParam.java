package com.mrl.pastry.portal.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Weixin login parameters
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@Data
public class WxLoginParam {

    @NotBlank(message = "Js_code cannot be blank")
    private String code;

    @NotBlank(message = "User's encryptedData cannot be blank")
    private String encryptedData;

    @NotBlank(message = "iv must not be blank")
    private String iv;
}
