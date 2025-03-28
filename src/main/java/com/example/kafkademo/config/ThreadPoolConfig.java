package com.example.kafkademo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 */
@Configuration
class ThreadPoolConfig {

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 8;
    /**
     * 最大线程数
     */
    private static final int MAX_POLL_SIZE = 16;
    /**
     * 核心线程 < 当前线程数 < 最大线程, 大于核心线程数的线程没有任务，停止线程时间
     */
    private static final int KEEP_ALIVE_TIME = 60;

    /**
     * 线程队列的大小
     */
    private static final int QUEUE_CAPACITY = 10000;
    /**
     * 线程池名称前缀
     */
    private static final String THREAD_NAME_PREFIX = "send-pool-";


    @Bean("sendThreadPool")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(MAX_POLL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        taskExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        // 线程池拒绝策略，AbortPolicy:拒绝，CallerRunsPolicy:本线程执行，DiscardPolicy：丢弃，DiscardOldestPolicy：弃老原则
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }
}
