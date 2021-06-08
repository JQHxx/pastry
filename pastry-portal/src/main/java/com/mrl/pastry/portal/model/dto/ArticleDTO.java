package com.mrl.pastry.portal.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Article dto
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/11
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ArticleDTO extends BlogBaseDTO {

    private String title;

    private String summary;

    private Integer wordCount;
}
