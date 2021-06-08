package com.mrl.pastry.upload.controller;

import com.mrl.pastry.upload.service.AttachmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Attachment controller
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/21
 */
@RestController
@RequestMapping("/file")
public class UploadController {

    private final AttachmentService attachmentService;

    public UploadController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping
    @ApiOperation("Uploads a file")
    public String uploadAttachment(@RequestPart("file") MultipartFile file) {
        return attachmentService.upload(file);
    }

    @PostMapping(value = "/delete")
    @ApiOperation("Deletes files")
    public void deleteAttachment(@RequestBody List<String> fileKeys) {
        attachmentService.deleteByFileKey(fileKeys);
    }
}
