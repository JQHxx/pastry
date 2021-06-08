package com.mrl.pastry.upload.handler.file;

import com.mrl.pastry.upload.config.properties.TencentCosProperties;
import com.mrl.pastry.upload.model.enums.UploadType;
import com.mrl.pastry.upload.model.support.UploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * Tencent cos file handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/8
 */
public class TencentCosFileHandler implements FileHandler {

    @Autowired
    private TencentCosProperties tencentCosProperties;

    @Override
    public UploadResult upload(MultipartFile file) {
        return null;
    }

    @Override
    public void delete(String fileKey) {

    }

    @Override
    public UploadType getUploadType() {
        return UploadType.TENCENTYUN;
    }
}
