package com.mrl.pastry.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.pastry.upload.model.entity.Attachment;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Attachment service
 *
 * @author MrL
 * @since 2021-03-07
 */
public interface AttachmentService extends IService<Attachment> {

    /**
     * Upload a file
     *
     * @param file file must not be null
     * @return file access path
     */
    @Transactional(rollbackFor = Exception.class)
    String upload(@NonNull MultipartFile file);

    /**
     * Upload attachments
     * 由于小程序前端上传组件的限制(只能单张上传)，该接口暂时没法调用
     *
     * @param files files must not be null
     * @return list of attachments
     */
    List<String> upload(@NonNull MultipartFile[] files);

    /**
     * Delete attachment by fileKey
     *
     * @param fileKeys attachment fileKeys
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteByFileKey(@NonNull List<String> fileKeys);
}
