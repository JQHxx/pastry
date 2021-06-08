package com.mrl.pastry.portal.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Wx userinfo
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/15
 */
@Data
public class WxAuthenticateParam {

    @NotBlank(message = "授权码不能为空")
    private String code;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    private Boolean gender;

    @NotBlank(message = "头像不能为空")
    private String avatarUrl;
}
