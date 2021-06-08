package com.mrl.pastry.portal.model.dto;

import lombok.Data;

/**
 * Base user dto
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/16
 */
@Data
public class UserBaseDTO {

    private String id;

    private String avatar;

    private String nickname;
}
