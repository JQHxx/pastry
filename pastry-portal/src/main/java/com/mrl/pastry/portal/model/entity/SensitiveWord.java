package com.mrl.pastry.portal.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SensitiveWord entity
 *
 * @author MrL
 * @version 1.0
 * @date 2021/6/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Sensitive对象", description = "")
public class SensitiveWord {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "违禁词")
    private String word;
}
