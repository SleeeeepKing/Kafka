package com.example.kafkademo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class RedisConfigInitializer {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 可以从配置文件或数据库加载
    private static final Map<String, TenantConfigDomain> initialTenantConfig = Map.of(
            "tenantA", new TenantConfigDomain(0, 0, TenantStatusEnum.NORMAL, 150, 100, 200, 0),
            "tenantB", new TenantConfigDomain(0, 0, TenantStatusEnum.NORMAL, 30, 10, 200, 0),
            "tenantC", new TenantConfigDomain(0, 0, TenantStatusEnum.NORMAL, 20, 10, 200, 0)
    );
    // 可以从配置文件或数据库加载
    private static final Map<String, CustomsServerStatus> initialServerStatus = Map.of(
            "customsServer", new CustomsServerStatus( 0, 0, 1,200,80),
            "ceoneServer", new CustomsServerStatus( 0, 0, 1,200,200)
    );

    @PostConstruct
    public void initTenantConfig() {
        initialTenantConfig.forEach((tenantId, config) -> {
            String hashKey = "tenant:" + tenantId + ":config";
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
            Map<String, String> map = new HashMap<>();
            map.put("total", config.getTotal().toString());
            map.put("success", config.getSuccess().toString());
            map.put("isAlive", config.getIsAlive().toString());

            redisTemplate.opsForHash().putAll(hashKey, map);
        });
    }

}