package com.mrl.pastry.portal.feign;

import com.mrl.pastry.upload.api.UploadService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Upload Service
 *
 * @author MrL
 * @version 1.0
 * @date 2021/6/7
 */
@FeignClient(value = "pastry-upload", configuration = FeignConfig.class)
public interface FeignUploadService extends UploadService {
}
