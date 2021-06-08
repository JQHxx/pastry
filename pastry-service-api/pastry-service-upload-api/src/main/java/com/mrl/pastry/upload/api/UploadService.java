package com.mrl.pastry.upload.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Upload feign client service
 *
 * @author MrL
 * @version 1.0
 * @date 2021/6/7
 */
public interface UploadService {

    /**
     * Deletes attachments
     *
     * @param fileKeys attachment accessPaths
     */
    @PostMapping("/file/delete")
    void deleteAttachments(@RequestBody List<String> fileKeys);
}
