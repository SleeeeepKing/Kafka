package com.example.kafkademo.util;

import com.example.kafkademo.config.TenantConfigDomain;
import com.example.kafkademo.config.TenantStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class TenantConfigUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public TenantConfigDomain getTenantConfig(String tenantId) {
        String hashKey = "tenant:" + tenantId + ":config";
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(hashKey);
        if (entries.isEmpty()) return null;

        return new TenantConfigDomain(
                Integer.parseInt(entries.getOrDefault("total", "0").toString()),
                Integer.parseInt(entries.getOrDefault("success", "0").toString()),
                TenantStatusEnum.valueOf(entries.getOrDefault("state", "NORMAL").toString()),
                Integer.parseInt(entries.getOrDefault("fetchCount", "1000").toString()),
                Integer.parseInt(entries.getOrDefault("minFetchCount", "100").toString()),
                Integer.parseInt(entries.getOrDefault("maxFetchCount", "1000").toString()),
                Integer.parseInt(entries.getOrDefault("leftOver", "0").toString())
        );
    }


    public void updateTenantConfig(String tenantId, TenantConfigDomain config) {
        String hashKey = "tenant:" + tenantId + ":config";
        Map<String, String> map = new HashMap<>();
        map.put("total", config.getTotal().toString());
        map.put("success", config.getSuccess().toString());
        map.put("state", config.getState().name());
        map.put("fetchCount", config.getFetchCount().toString());
        map.put("minFetchCount", config.getMinFetchCount().toString());

        redisTemplate.opsForHash().putAll(hashKey, map);
    }

    public void incrementTotal(String tenantId){
        redisTemplate.opsForHash().increment("tenant:" + tenantId + ":config", "total", 1);
    }

    public void incrementSuccess(String tenantId){
        redisTemplate.opsForHash().increment("tenant:" + tenantId + ":config", "success", 1);
    }

    public void resetStatistics(String tenantId){
        redisTemplate.opsForHash().put("tenant:" + tenantId + ":config", "total", "0");
        redisTemplate.opsForHash().put("tenant:" + tenantId + ":config", "success", "0");
    }
    public void resetAllStatistics(){
        Set<String> tenantKeys = redisTemplate.keys("tenant:*:config");
        if (tenantKeys != null && !tenantKeys.isEmpty()) {
            for (String tenantKey : tenantKeys) {
                Map<Object, Object> tenantData = redisTemplate.opsForHash().entries(tenantKey);
                if (tenantData.isEmpty()) continue;
                resetStatistics(tenantKey.split(":")[1]);
            }
        }
    }

    public List<String> getAllTenantIds(){
        return List.of("tenantA", "tenantB", "tenantC");
    }


}
