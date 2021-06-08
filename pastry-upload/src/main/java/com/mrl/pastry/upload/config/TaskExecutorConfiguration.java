package com.mrl.pastry.upload.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Async task configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/27
 */
@Data
@Configuration
public class TaskExecutorConfiguration {

    @Value("${async.task.corePoolSize:5}")
    private int corePoolSize;

    @Value("${async.task.maxPoolSize:20}")
    private int maxPoolSize;

    @Value("${async.task.queueCapacity:2000}")
    private int queueCapacity;

    @Value("${async.task.keepAliveSeconds:60}")
    private int keepAliveSeconds;

    @Value("${async.task.threadNamePrefix:portal-}")
    private String threadNamePrefix;

    @Bean("executor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
