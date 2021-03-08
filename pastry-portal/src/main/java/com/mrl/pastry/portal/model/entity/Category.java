package com.mrl.pastry.portal.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author MrL
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Category对象", description = "")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "category name")
    private String name;

    @ApiModelProperty(value = "category description")
    private String description;

    @ApiModelProperty(value = "cover thumbnail")
    private String thumbnail;

    @ApiModelProperty(value = "parent category")
    private Integer parentId;

    @ApiModelProperty(value = "create_time")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @ApiModelProperty(value = "update_time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;


}
