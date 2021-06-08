package com.mrl.pastry.portal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * comment dto
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/16
 */
@Data
public class CommentDTO {

    private String id;

    private String content;

    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;
}
