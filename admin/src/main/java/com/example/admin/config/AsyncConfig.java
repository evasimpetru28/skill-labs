package com.example.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    // The @EnableAsync annotation enables Spring's ability to run @Async methods in a background thread pool
    
    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);          // Number of core threads
        executor.setMaxPoolSize(4);           // Maximum number of threads
        executor.setQueueCapacity(500);       // Queue capacity for tasks
        executor.setThreadNamePrefix("EmailThread-");
        executor.initialize();
        return executor;
    }
} 