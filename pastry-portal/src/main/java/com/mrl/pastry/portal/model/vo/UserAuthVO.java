package com.mrl.pastry.portal.model.vo;

import com.mrl.pastry.portal.model.dto.UserDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * UserAuthVO
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/31
 */
@Data
@AllArgsConstructor
public class UserAuthVO {

    private UserDetailDTO user;

    private String token;
}
