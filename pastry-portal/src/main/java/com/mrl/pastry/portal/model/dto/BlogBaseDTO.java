package com.mrl.pastry.portal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Base blog dto
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/11
 */
@Data
public class BlogBaseDTO {

    private String id;

    private String content;

    private String attachments;

    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;
}
