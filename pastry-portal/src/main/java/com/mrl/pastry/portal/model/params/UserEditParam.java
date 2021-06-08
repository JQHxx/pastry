package com.mrl.pastry.portal.model.params;

import com.mrl.pastry.common.support.ParamConverter;
import com.mrl.pastry.portal.model.dto.UserDetailDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * User edit-params
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/15
 */
@Data
public class UserEditParam implements ParamConverter<UserDetailDTO> {

    @NotBlank(message = "头像不能为空")
    private String avatar;

    @Size(max = 20, message = "个人简介长度不能超过 {max}")
    private String profile;

    @NotBlank(message = "昵称不能为空")
    @Size(max = 8, message = "昵称长度不能超过 {max}")
    private String nickname;

    private Boolean gender = true;
}
