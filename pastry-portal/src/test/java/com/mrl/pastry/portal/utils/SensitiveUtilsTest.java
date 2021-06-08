package com.mrl.pastry.portal.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author MrL
 * @version 1.0
 * @date 2021/6/1
 */
@SpringBootTest
public class SensitiveUtilsTest {

    @Autowired
    private SensitiveUtils sensitiveUtils;

    @Test
    public void test() {
        String content = "";
        String refine = sensitiveUtils.refine(content);
        System.out.println(refine);
    }
}
