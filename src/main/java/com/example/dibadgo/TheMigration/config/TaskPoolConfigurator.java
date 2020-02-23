package com.example.dibadgo.TheMigration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class TaskPoolConfigurator implements AsyncConfigurer {

    private static final int MAX_POOL_SIZE = 100;
    private static final int CORE_POOL_SIZE = 75;
    private static final int QUEUE_CAPACITY = 75;

    private static final String THREAD_PREFIX = "TheMigrationExecutor";

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadExecutor = new ThreadPoolTaskExecutor();
        threadExecutor.setCorePoolSize(CORE_POOL_SIZE);
        threadExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        threadExecutor.setQueueCapacity(QUEUE_CAPACITY);
        threadExecutor.setThreadNamePrefix(THREAD_PREFIX);
        threadExecutor.initialize();
        return threadExecutor;
    }
}
