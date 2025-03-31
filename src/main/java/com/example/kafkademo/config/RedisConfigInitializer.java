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
            "tenantA", new TenantConfigDomain(0, 0, TenantStatusEnum.NORMAL, 10, 2, 15, 0, 0),
            "tenantB", new TenantConfigDomain(0, 0, TenantStatusEnum.NORMAL, 1, 1, 3, 0, 0),
            "tenantC", new TenantConfigDomain(0, 0, TenantStatusEnum.NORMAL, 1, 1, 3, 0, 0)
    );
    // 可以从配置文件或数据库加载
    private static final Map<String, CustomsServerStatus> initialServerStatus = Map.of(
            "customsServer", new CustomsServerStatus( 0, 0, 1,30,26),
            "ceoneServer", new CustomsServerStatus( 0, 0, 1,200,50)
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
            map.put("fetchCount", config.getFetchCount().toString());
            map.put("minFetchCount", config.getMinFetchCount().toString());
            map.put("maxFetchCount", config.getMaxFetchCount().toString());
            map.put("leftOver", config.getLeftOver().toString());

            redisTemplate.opsForHash().putAll(hashKey, map);
        });

        initialServerStatus.forEach((server, config) -> {
            String hashKey = "server:" + server + ":status";
            // 强制删除旧的数据
            redisTemplate.delete(hashKey);
            Map<String, String> map = new HashMap<>();
            map.put("total", config.getTotal().toString());
            map.put("success", config.getSuccess().toString());
            map.put("isAlive", config.getIsAlive().toString());
            map.put("capacity", config.getCapacity().toString());
            map.put("quota", config.getQuota().toString());

            redisTemplate.opsForHash().putAll(hashKey, map);
        });
    }

}