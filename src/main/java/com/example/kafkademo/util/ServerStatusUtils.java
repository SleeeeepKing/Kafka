package com.example.kafkademo.util;

import com.example.kafkademo.config.CustomsServerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class ServerStatusUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public CustomsServerStatus getServerStatus(String serverName) {
        String hashKey = "server:" + serverName + ":status";
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(hashKey);
        if (entries.isEmpty()) return null;

        return new CustomsServerStatus(
                Integer.parseInt(entries.getOrDefault("total", "0").toString()),
                Integer.parseInt(entries.getOrDefault("success", "0").toString()),
                Integer.parseInt(entries.getOrDefault("isAlive", "1").toString()),
                Integer.parseInt(entries.getOrDefault("capacity", "200").toString()),
                Integer.parseInt(entries.getOrDefault("quota", "80").toString())
        );
    }


    public void updateServerStatus(String serverName, CustomsServerStatus status) {
        String hashKey = "server:" + serverName + ":status";
        Map<String, String> map = new HashMap<>();
        map.put("total", status.getTotal().toString());
        map.put("success", status.getSuccess().toString());
        map.put("isAlive", status.getIsAlive().toString());

        redisTemplate.opsForHash().putAll(hashKey, map);
    }

    public Integer getServerIsAlive(String serverName) {
        return Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForHash().get("server:" + serverName + ":status", "isAlive")).toString());
    }

    public void incrementTotal(String serverName) {
        redisTemplate.opsForHash().increment("server:" + serverName + ":status", "total", 1);
    }

    public void incrementSuccess(String serverName) {
        redisTemplate.opsForHash().increment("server:" + serverName + ":status", "success", 1);
    }

    public void resetStatistics(String serverName) {
        redisTemplate.opsForHash().put("server:" + serverName + ":status", "total", "0");
        redisTemplate.opsForHash().put("server:" + serverName + ":status", "success", "0");
    }

    public double getSuccessRate(String serverName) {
        CustomsServerStatus status = getServerStatus(serverName);
        if (status == null || status.getTotal() == 0) {
            return 1.0;
        }
        return (double) status.getSuccess() / status.getTotal();
    }

}
