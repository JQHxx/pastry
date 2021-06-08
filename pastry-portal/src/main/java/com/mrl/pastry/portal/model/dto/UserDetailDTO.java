package com.mrl.pastry.portal.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User detail dto
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailDTO extends UserBaseDTO {

    private Boolean gender;

    private String profile;
}
