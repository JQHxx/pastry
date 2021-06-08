package com.mrl.pastry.upload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.common.utils.SecurityUtils;
import com.mrl.pastry.upload.handler.file.FileHandler;
import com.mrl.pastry.upload.mapper.AttachmentMapper;
import com.mrl.pastry.upload.model.entity.Attachment;
import com.mrl.pastry.upload.model.support.UploadResult;
import com.mrl.pastry.upload.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Attachment service implementation
 *
 * @author MrL
 * @since 2021-03-07
 */
@Slf4j
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    private final FileHandler fileHandler;

    public AttachmentServiceImpl(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public String upload(@NonNull MultipartFile file) {
        Assert.notNull(file, "file must not be null");

        UploadResult uploadResult = fileHandler.upload(file);
        Attachment attachment = uploadResult.convertTo();
        attachment.setUploader(SecurityUtils.getCurrentUserId());
        attachment.setUploadType(fileHandler.getUploadType());

        // 保存上传记录
        this.baseMapper.insert(attachment);
        log.debug("insert attachment: [{}]", attachment);
        return attachment.getAccessPath();
    }

    @Override
    public List<String> upload(@NonNull MultipartFile[] files) {
        Assert.notNull(files, "files cannot be null");
        List<String> list = Arrays.stream(files).map(this::upload).collect(Collectors.toList());
        log.debug("upload files: [{}]", list);
        return list;
    }

    @Override
    public void deleteByFileKey(@NonNull List<String> fileKeys) {
        Long userId = SecurityUtils.getCurrentUserId();
        log.debug("user: [{}] start delete files, batch size: [{}]", userId, fileKeys.size());
        for (String fileKey : fileKeys) {
            fileHandler.delete(fileKey);
            // 暂不删DB记录
            log.debug("delete file accessPath: [{}]", fileKey);
        }
    }
}
