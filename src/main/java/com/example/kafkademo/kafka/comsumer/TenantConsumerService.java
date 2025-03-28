package com.example.kafkademo.kafka.comsumer;

import cn.hutool.cache.impl.FIFOCache;
import com.example.kafkademo.client.ClientDTO;
import com.example.kafkademo.client.ClientService;
import com.example.kafkademo.config.CustomsServerStatus;
import com.example.kafkademo.config.TenantConfigDomain;
import com.example.kafkademo.exception.InternalServerException;
import com.example.kafkademo.kafka.handler.MessageHandler;
import com.example.kafkademo.kafka.producer.KafkaProducer;
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
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    @Autowired
    private KafkaProducer kafkaProducer;

    private static final int RATE_LIMIT_PER_BATCH = 20;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Deprecated
    public void consumeByBatch(List<ConsumerRecord<String, String>> records, Acknowledgment ack) throws JsonProcessingException {
//        if (records.isEmpty()) {
//            ack.acknowledge();
//            return;
//        }
//        int processedCount = 0;
//        for (ConsumerRecord<String, String> record : records) {
//            ClientDTO clientDTO = objectMapper.readValue(record.value(), ClientDTO.class);
//            TenantConfigDomain config = tenantConfigUtils.getTenantConfig(clientDTO.getTenantId());
//            if (config == null) {
//                log.info("未找到租户配置：{}", clientDTO.getTenantId());
//                continue;
//            }
//            CustomsServerStatus customsServerStatus = serverStatusUtils.getServerStatus("customsServer");
//            //         计算本轮可用额度
//            int minQuota = config.getMinFetchCount();
//            int requiredQuota = config.getFetchCount() <= minQuota ? 0 : config.getFetchCount() - minQuota;
//            int leftOver = acquireLeftoverQuota("server:customsServer:status", requiredQuota);
//            int quotaThisRound = customsServerStatus.getIsAlive() != 1 ? 1 : minQuota + leftOver; //动态竞争quota后得出的额度
//
//            if (processedCount >= RATE_LIMIT_PER_BATCH) {
//                break;
//            }
//            try {
//                consume(record, 1, ack);
//                processedCount++;
//            } catch (Exception e) {
//                log.error("Failed to process message", e);
//            }
//        }
    }


    public void consume(ConsumerRecord<String, String> record, int attemptCount, Acknowledgment ack) throws JsonProcessingException {
        if (Objects.isNull(record)) {
            ack.acknowledge();
            return;
        }

//        log.info("Received message[{}]: {}", attemptCount, record.value());
        // 幂等性校验，校验消息是否被重复消费
        String cacheKey = record.topic() + "_" + record.partition() + "_" + record.offset() + "_" + record.key();
//        log.info("cacheKey={}", cacheKey);
        if (fifoCache.containsKey(cacheKey)) {
            log.error("已经被消费，请勿重新消费...cacheKey={}", cacheKey);
            return;
        }
        fifoCache.put(cacheKey, true);

        try {
            // todo 这里改成目标服务器唯一标识
            CustomsServerStatus customsServerStatus = serverStatusUtils.getServerStatus("customsServer");

            ClientDTO clientDTO = objectMapper.readValue(record.value(), ClientDTO.class);
            String tenantId = clientDTO.getTenantId();
            TenantConfigDomain config = tenantConfigUtils.getTenantConfig(tenantId);
            if (config == null) {
                log.info("未找到租户配置：{}", tenantId);
                return;
            }

            // todo 找到租户的Handler发送消息给海关，并记录结果。如果是4XX报错则不进行重试
            MessageHandler handler = tenantHandlerMapping.getHandler(tenantId);
            // todo ⬇️在这一行执行之际推送代码
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
//                log.info("success: {}", tenantConfigUtils.getTenantConfig(tenantId).toString());
            } else {
//                log.info("failed: {}", tenantConfigUtils.getTenantConfig(tenantId).toString());
//                throw new InternalServerException("Failed to send message to customs server");
            }
//                });
//            });
            log.info("{} 消息处理完成: {}", tenantId, tenantConfigUtils.getTenantConfig(tenantId));
        } catch (Exception e) {
            if (attemptCount > 2) {
                // 送入死信队列
                kafkaProducer.sendMessage("client-topic.dlt", record.value());
            } else if (attemptCount == 2) {
                // 送入重试5分钟队列, 测试时使用4秒
                kafkaProducer.sendDelayedMessage("client-topic-retry-4000", record.value(), 4);

            } else {
                // 送入重试1分钟队列 , 测试时使用2秒
                kafkaProducer.sendDelayedMessage("client-topic-retry-2000", record.value(), 2);
            }
//            log.error("Failed to process message", e);
            fifoCache.remove(cacheKey);
            ack.acknowledge();
        }
    }


}
