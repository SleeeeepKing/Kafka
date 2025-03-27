package com.example.kafkademo.kafka.comsumer;

import cn.hutool.cache.impl.FIFOCache;
import com.example.kafkademo.client.ClientDTO;
import com.example.kafkademo.client.ClientService;
import com.example.kafkademo.config.CustomsServerStatus;
import com.example.kafkademo.config.TenantConfigDomain;
import com.example.kafkademo.exception.InternalServerException;
import com.example.kafkademo.kafka.handler.MessageHandler;
import com.example.kafkademo.util.ServerStatusUtils;
import com.example.kafkademo.util.TenantConfigUtils;
import com.example.kafkademo.util.TenantHandlerMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TenantConsumerService {
    @Autowired
    private TenantHandlerMapping handlerMapping;
    @Autowired
    private TenantConfigUtils tenantConfigUtils;
    @Autowired
    private ServerStatusUtils serverStatusUtils;
    @Autowired
    private TenantHandlerMapping tenantHandlerMapping;
    @Autowired
    private ClientService clientService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private FIFOCache<String, Boolean> fifoCache;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public void consume(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("Received message: {}", record.value());
        // 幂等性校验，校验消息是否被重复消费
        String cacheKey = record.topic() + "_" + record.partition() + "_" + record.offset() + "_" + record.key();
        log.info("cacheKey={}", cacheKey);
        if (fifoCache.containsKey(cacheKey)) {
            log.error("已经被消费，请勿重新消费...cacheKey={}", cacheKey);
            // TODO 错误消息通知
            return;
        }

        fifoCache.put(cacheKey, true);
        CustomsServerStatus customsServerStatus = serverStatusUtils.getServerStatus("customsServer");

//        if (records.isEmpty()) return;

        Map<String, List<ConsumerRecord<String, String>>> tenantGroupedRecords = new HashMap<>();

//        for (ConsumerRecord<String, String> record : records) {
//            try {
//                ClientDTO clientDTO = objectMapper.readValue(record.value(), ClientDTO.class);
//                String tenantId = clientDTO.getTenantId();
//                tenantGroupedRecords
//                        .computeIfAbsent(tenantId, k -> new LinkedList<>())
//                        .add(record);
//            } catch (JsonProcessingException e) {
//                log.error("Failed to parse message", e);
//            }
//        }
        try {
            ClientDTO clientDTO = objectMapper.readValue(record.value(), ClientDTO.class);
            String tenantId = clientDTO.getTenantId();
            tenantGroupedRecords
                    .computeIfAbsent(tenantId, k -> new LinkedList<>())
                    .add(record);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse message", e);
        }

        tenantGroupedRecords.forEach((tenantId, tenantMsgs) -> {
            TenantConfigDomain config = tenantConfigUtils.getTenantConfig(tenantId);
            if (config == null) {
                log.info("未找到租户配置：{}", tenantId);
                return;
            }

//            // 计算本轮可用额度
//            int minQuota = config.getMinFetchCount();
//            int requiredQuota = config.getFetchCount() <= minQuota ? 0 : config.getFetchCount() - minQuota;
//            int leftOver = acquireLeftoverQuota("server:customsServer:status", requiredQuota);
//            int quotaThisRound = customsServerStatus.getIsAlive() != 1 ? 1 : minQuota + leftOver; //动态竞争quota后得出的额度
//
//            List<ConsumerRecord<String, String>> tenantRecords = StreamSupport.stream(records.spliterator(), false)
//                    .filter(rec -> tenantId.equals(rec.key())).limit(quotaThisRound).toList();

//            records.forEach(record -> {
//                taskExecutor.submit(() -> {
            // 将tenantMsgs转换成申报用的DTO
//                    try {
//                        H7RequestDTO h7RequestDTO = objectMapper.readValue(record.value(), H7RequestDTO.class);
//                    } catch (JsonProcessingException e) {
//                        log.error("Failed to parse message", e);
//                    }
            // todo 找到租户的Handler发送消息给海关，并记录结果。如果是4XX报错则不进行重试
            MessageHandler handler = tenantHandlerMapping.getHandler(tenantId);
            String code = handler.handler(record.value());

            // 只负责统计消息总数和调用成功数
            tenantConfigUtils.incrementTotal(tenantId);
            serverStatusUtils.incrementTotal(tenantId);
            double serverSuccessRate = serverStatusUtils.getSuccessRate("serverA");
            if (serverSuccessRate < 0.3) {
                customsServerStatus.setIsAlive(0);
                serverStatusUtils.updateServerStatus(tenantId, customsServerStatus);
            } else {
                customsServerStatus.setIsAlive(1);
                serverStatusUtils.updateServerStatus(tenantId, customsServerStatus);
            }

            if ("200".equals(code)) {
                tenantConfigUtils.incrementSuccess(tenantId);
                serverStatusUtils.incrementSuccess(tenantId);
                log.info("success: {}", tenantConfigUtils.getTenantConfig(tenantId).toString());
            } else {
                log.info("failed: {}", tenantConfigUtils.getTenantConfig(tenantId).toString());
                throw new InternalServerException("Failed to send message to customs server");
            }
//                });
//            });
            log.info("{} 消息处理完成: {}", tenantId, tenantConfigUtils.getTenantConfig(tenantId));
        });
    }


    // 原子操作，从Redis安全地竞争额度
    private int acquireLeftoverQuota(String hashKey, int requestAmount) {
        // Lua脚本 (可直接内联，也可独立文件后加载成字符串)
        String script = """
                local quotaStr = redis.call("HGET", KEYS[1], "quota")
                if not quotaStr then
                   return 0
                end

                local quota = tonumber(quotaStr)
                if (not quota) or (quota <= 0) then
                   return 0
                end

                local want = tonumber(ARGV[1])
                if not want then
                   return 0
                end

                local toTake = math.min(quota, want)
                redis.call("HINCRBY", KEYS[1], "quota", -toTake)
                return toTake
                """;

        // 构造RedisScript对象, 指定返回类型为Long
        RedisScript<Long> redisScript = RedisScript.of(script, Long.class);

        // 执行脚本
        Long result = redisTemplate.execute(
                redisScript,
                List.of(hashKey),   // 传入KEYS
                String.valueOf(requestAmount)  // 传入ARGV
        );

        // 如果result为null或其他情况，返回0
        return result == null ? 0 : result.intValue();
    }
}
