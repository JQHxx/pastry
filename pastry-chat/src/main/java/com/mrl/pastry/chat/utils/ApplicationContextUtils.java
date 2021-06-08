package com.mrl.pastry.chat.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Application context utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/4
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @NonNull
    public static <T> T getBean(@NonNull Class<T> clazz) {
        Assert.notNull(clazz, "class must not be null");
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }
}
