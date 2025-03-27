package com.example.kafkademo.config;

import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.core.date.DateUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FIFO内存缓存
 **/
@Configuration
public class FIFOCacheConfig {
    @Bean
    public FIFOCache<String,Boolean> fifoCache() {
        return new FIFOCache(10000, DateUnit.MINUTE.getMillis() * 30);
    }
}
