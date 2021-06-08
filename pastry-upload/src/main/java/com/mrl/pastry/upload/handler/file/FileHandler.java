package com.mrl.pastry.upload.handler.file;

import com.mrl.pastry.upload.model.enums.UploadType;
import com.mrl.pastry.upload.model.support.UploadResult;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Supplier;

/**
 * File handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/8
 */
public interface FileHandler {

    String PROTOCOL_HTTPS = "https://";

    MediaType IMAGE_TYPE = MediaType.valueOf("image/*");

    /**
     * Check if the file is an image
     *
     * @param file file must not be null
     * @return true if the file is an image, false otherwise
     */
    default boolean isImageType(@NonNull MultipartFile file) {
        String fileType = file.getContentType();
        return fileType != null && IMAGE_TYPE.includes(MediaType.valueOf(fileType));
    }

    /**
     * Handle image meta data
     *
     * @param file              multipart file cannot be null
     * @param uploadResult      upload result cannot be null
     * @param thumbnailSupplier thumbnail supplier
     */
    default void handleImageMetaData(@NonNull MultipartFile file,
                                     @NonNull UploadResult uploadResult,
                                     @Nullable Supplier<String> thumbnailSupplier) {
        /*if (isImageType(file)) {
            try {
                BufferedImage read = ImageIO.read(file.getInputStream());
                uploadResult.setWidth(read.getWidth());
                uploadResult.setHeight(read.getHeight());
            } catch (Exception | OutOfMemoryError e) {
                LoggerFactory.getLogger(this.getClass()).warn("Failed to get image meta data", e);
            }
        }*/
        if (thumbnailSupplier != null) {
            uploadResult.setThumbnailPath(thumbnailSupplier.get());
        }
    }

    /**
     * Upload file
     *
     * @param file multipart file must not be null
     * @return UploadResult
     */
    UploadResult upload(@NonNull MultipartFile file);

    /**
     * Delete file
     *
     * @param accessPath access path must not be null
     */
    void delete(@NonNull String accessPath);

    /**
     * Get upload type
     *
     * @return UploadType
     */
    UploadType getUploadType();
}
