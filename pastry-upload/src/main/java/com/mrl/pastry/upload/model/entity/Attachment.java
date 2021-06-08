package com.mrl.pastry.upload.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mrl.pastry.upload.model.enums.UploadType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Attachment entity
 *
 * @author MrL
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Attachment对象", description = "")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "user id")
    private Long uploader;

    @ApiModelProperty(value = "original file name")
    private String originalFilename;

    @ApiModelProperty(value = "suffix: png, jpg, mp4, zip...")
    private String suffix;

    @ApiModelProperty(value = "media type")
    private String mediaType;

    @ApiModelProperty(value = "oss yun filename")
    private String uploadFilename;

    @ApiModelProperty(value = "upload type: aliyun, qiniu...")
    private UploadType uploadType;

    @ApiModelProperty(value = "access path")
    private String accessPath;

    @ApiModelProperty(value = "thumbnail access path")
    private String thumbnailPath;

    @ApiModelProperty(value = "size(byte)")
    private Long size;

    @ApiModelProperty(value = "width")
    private Integer width;

    @ApiModelProperty(value = "height")
    private Integer height;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "create time")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "update time")
    private Date updateTime;
}
