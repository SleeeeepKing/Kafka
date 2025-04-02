package com.example.kafkademo.config;

import com.example.kafkademo.config.dto.CustomsServerStatus;
import com.example.kafkademo.config.dto.TenantConfigDomain;
import com.example.kafkademo.config.enums.TenantStatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RedisConfigInitializer {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 可以从配置文件或数据库加载
    private static final Map<String, TenantConfigDomain> initialTenantConfig = Map.of(
            "tenantA", new TenantConfigDomain(0, 0, TenantStatusEnum.NORMAL, 0, 0,150),
            "tenantB", new TenantConfigDomain(0, 0, TenantStatusEnum.NORMAL, 0, 0,50),
            "tenantC", new TenantConfigDomain(0, 0, TenantStatusEnum.NORMAL, 0, 0,50),
            "tenantCustoms", new TenantConfigDomain(0, 0, TenantStatusEnum.NORMAL, 0, 0,200)
    );

    @PostConstruct
    public void initTenantConfig() {
        initialTenantConfig.forEach((tenantId, config) -> {
            String hashKey = "tenant:" + tenantId + ":config";
            // 强制删除旧的数据
            redisTemplate.delete(hashKey);
            Map<String, String> map = new HashMap<>();
            map.put("total", config.getTotal().toString());
            map.put("success", config.getSuccess().toString());
            map.put("state", config.getState().name());
            map.put("leftOver", config.getLeftOver().toString());
            map.put("detection", config.getDetection().toString());
            redisTemplate.opsForHash().putAll(hashKey, map);
        });
    }

}