package com.xu.atchat.config.thread;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 18:23
 * @description 异步处理配置
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        taskExecutor.setCorePoolSize(4);
        //最大线程数
        taskExecutor.setMaxPoolSize(8);
        //队列大小
        taskExecutor.setQueueCapacity(100);
        //定义前缀
        taskExecutor.setThreadNamePrefix("AT-async-pool-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
