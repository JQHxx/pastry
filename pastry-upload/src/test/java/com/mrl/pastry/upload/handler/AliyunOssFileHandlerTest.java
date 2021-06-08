package com.mrl.pastry.upload.handler;

import com.mrl.pastry.upload.handler.file.AliyunOssFileHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author MrL
 * @version 1.0
 * @date 2021/4/12
 */
@SpringBootTest
public class AliyunOssFileHandlerTest {

    @Autowired
    AliyunOssFileHandler fileHandler;

    @Test
    void singletonTest() throws InterruptedException {
        String fileKey = "https://mrl-pastry.oss-cn-shenzhen.aliyuncs.com/d9a9fdaa055342ecb3c3142cb9e8a5bf.jpg";
        System.out.println(fileKey.substring(fileKey.lastIndexOf("/") + 1));
    }
}
