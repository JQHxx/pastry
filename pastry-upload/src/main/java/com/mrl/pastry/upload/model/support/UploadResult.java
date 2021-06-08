package com.mrl.pastry.upload.model.support;

import com.mrl.pastry.common.support.ParamConverter;
import com.mrl.pastry.upload.model.entity.Attachment;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.MediaType;

/**
 * Upload result
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/10
 */
@Data
@Accessors(chain = true)
public class UploadResult implements ParamConverter<Attachment> {

    private String originalFilename;

    private String suffix;

    private MediaType mediaType;

    private String uploadFilename;

    private String accessPath;

    private Long size;

    private String thumbnailPath;

    private Integer width;

    private Integer height;
}
