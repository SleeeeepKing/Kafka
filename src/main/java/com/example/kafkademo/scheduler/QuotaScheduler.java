package com.example.kafkademo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class QuotaScheduler {

    @Autowired
    private StringRedisTemplate redisTemplate;
    // 每5秒重置server的剩余quota
    @Scheduled(fixedRate = 5000)
    public void resetServerQuota() {
        String serverName = "customsServer"; // 或者从配置中获取
        String serverKey = "server:" + serverName + ":status";

        // 1) 读出服务的总容量
        //    你可以在serverHash里存 "capacity" 或 "total" 字段名称，自己定义
        Map<Object, Object> serverMap = redisTemplate.opsForHash().entries(serverKey);
        if (serverMap.isEmpty()) {
            System.out.println("serverKey " + serverKey + " 不存在，跳过。");
            return;
        }

        int serverTotalCapacity = safeParseInt(serverMap.getOrDefault("capacity", "200"));

        // 2) 遍历所有 tenant:*:config，拿到 minFetchCount 求和
        int sumMinFetchCount = 0;
        Set<String> tenantKeys = redisTemplate.keys("tenant:*:config");
        if (tenantKeys != null && !tenantKeys.isEmpty()) {
            for (String tenantKey : tenantKeys) {
                Map<Object, Object> tenantData = redisTemplate.opsForHash().entries(tenantKey);
                if (tenantData.isEmpty()) continue;

                int minFetch = safeParseInt(tenantData.getOrDefault("minFetchCount", "0"));
                sumMinFetchCount += minFetch;
            }
        }

        // 3) leftover = serverTotalCapacity - sumMinFetchCount
        int leftover = serverTotalCapacity - sumMinFetchCount;
        if (leftover < 0) leftover = 0;

        // 4) 把 leftover写回 "server:xxx:status" hash中的 quota 字段
        redisTemplate.opsForHash().put(serverKey, "quota", String.valueOf(leftover));

        System.out.printf("【%s】serverTotal=%d, sum(minFetch)=%d, leftover=%d%n",
                serverName, serverTotalCapacity, sumMinFetchCount, leftover);
    }

    private int safeParseInt(Object obj) {
        if (obj == null) return 0;
        String str = String.valueOf(obj).trim();
        if (str.isEmpty()) return 0;
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0; // 或另行处理
        }
    }
}
