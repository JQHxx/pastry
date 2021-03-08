package com.mrl.pastry.portal.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@ApiModel(value = "Attachment对象", description = "")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "file name")
    private String name;

    @ApiModelProperty(value = "file description")
    private String description;

    @ApiModelProperty(value = "suffix: png, jpg, mp4, zip...")
    private String suffix;

    @ApiModelProperty(value = "http access path")
    private String path;

    @ApiModelProperty(value = "thumb access path")
    private String thumbPath;

    @ApiModelProperty(value = "oss/yun file key")
    private String fileKey;

    @ApiModelProperty(value = "media type")
    private String mediaType;

    @ApiModelProperty(value = "upload type: aliyun oss, qiniu...")
    private Integer uploadType;

    @ApiModelProperty(value = "width")
    private Integer width;

    @ApiModelProperty(value = "height")
    private Integer height;

    @ApiModelProperty(value = "size(byte)")
    private Integer size;

    @ApiModelProperty(value = "create time")
    private Date createTime;

    @ApiModelProperty(value = "update time")
    private Date updateTime;

    @ApiModelProperty(value = "deleted flag")
    @TableLogic
    private Boolean deleted;

}
