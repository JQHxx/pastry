package com.mrl.pastry.upload.handler.file;

import com.mrl.pastry.upload.config.properties.QiniuyunOssProperties;
import com.mrl.pastry.upload.model.enums.UploadType;
import com.mrl.pastry.upload.model.support.UploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * Qiniuyun oss file handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/8
 */
public class QiniuyunOssFileHandler implements FileHandler {

    @Autowired
    private QiniuyunOssProperties qiniuyunOssProperties;

    @Override
    public UploadResult upload(MultipartFile file) {
        return null;
    }

    @Override
    public void delete(@NonNull String fileKey) {

    }

    @Override
    public UploadType getUploadType() {
        return UploadType.QINIUYUN;
    }
}
