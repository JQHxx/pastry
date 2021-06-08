package com.mrl.pastry.portal.model.vo;

import com.mrl.pastry.portal.model.dto.UserDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Follow vo
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/27
 */
@Data
@AllArgsConstructor
public class FollowVO {

    private UserDetailDTO user;

    private Boolean follow;
}
