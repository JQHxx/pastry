package com.mrl.pastry.upload.handler.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.mrl.pastry.upload.config.properties.AliyunOssProperties;
import com.mrl.pastry.upload.model.enums.UploadType;
import com.mrl.pastry.upload.model.support.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * Aliyun oss file handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/8
 */
@Slf4j
public class AliyunOssFileHandler implements FileHandler {

    @Autowired
    private AliyunOssProperties properties;

    @Autowired
    private OSS client;

    @Resource(name = "executor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public UploadResult upload(@NonNull MultipartFile file) {
        Assert.notNull(file, "file must not be null");

        String fileType = file.getContentType();
        Objects.requireNonNull(fileType, "file type is null");

        String originalFilename = file.getOriginalFilename();
        Objects.requireNonNull(originalFilename, "file name is null");

        String suffix = FileUtil.getSuffix(originalFilename);
        String uploadFilename = IdUtil.fastSimpleUUID() + "." + suffix;

        //e.g. https://mrl-oss.oss-cn-shenzhen.aliyuncs.com/xxx.jpg
        String accessPath = PROTOCOL_HTTPS +
                properties.getBucketName() + "." +
                properties.getEndpoint() + "/" +
                uploadFilename;

        // 优化：异步上传
        taskExecutor.execute(() -> {
            try {
                log.debug("start upload file: [{}] ,upload type: [{}]", this.getUploadType(), originalFilename);
                client.putObject(properties.getBucketName(), uploadFilename, file.getInputStream());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        });

        final UploadResult uploadResult = new UploadResult()
                .setOriginalFilename(originalFilename)
                .setSuffix(suffix)
                .setMediaType(MediaType.valueOf(fileType))
                .setUploadFilename(uploadFilename)
                .setAccessPath(accessPath)
                .setSize(file.getSize());

        // set image meta data
        handleImageMetaData(file, uploadResult, null);

        log.debug("Upload result: [{}]", uploadResult);
        return uploadResult;
    }

    @Override
    public void delete(@NonNull String fileKey) {
        Assert.notNull(fileKey, "access path cannot be null");
        taskExecutor.execute(() -> client.deleteObject(properties.getBucketName(), fileKey));
    }

    @Override
    public UploadType getUploadType() {
        return UploadType.ALIYUN;
    }
}
