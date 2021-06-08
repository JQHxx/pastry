package com.mrl.pastry.portal.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Blog statistic vo
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/11
 */
@Data
@AllArgsConstructor
public class BlogStatisticVO {

    private Integer visitCount;

    private Integer coinCount;

    private Integer commentCount;

    private Integer likeCount;
}
