package com.mrl.pastry.portal.model.vo;

import com.mrl.pastry.portal.model.dto.BlogBaseDTO;
import com.mrl.pastry.portal.model.dto.UserBaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Blog vo
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogVO extends BlogBaseDTO {

    private BlogStatisticVO statistic;

    private UserBaseDTO user;

    private Boolean like;
}
