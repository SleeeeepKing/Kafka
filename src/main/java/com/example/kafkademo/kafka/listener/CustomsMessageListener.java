package com.example.kafkademo.kafka.listener;

import com.example.kafkademo.config.dto.TenantConfigDomain;
import com.example.kafkademo.kafka.comsumer.TenantConsumerService;
import com.example.kafkademo.kafka.producer.KafkaProducer;
import com.example.kafkademo.kafka.strategy.AdaptiveKafkaConsumer;
import com.example.kafkademo.scheduler.QuotaScheduler;
import com.example.kafkademo.util.ServerStatusUtils;
import com.example.kafkademo.util.TeamsNotificationService;
import com.example.kafkademo.util.TenantConfigUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomsMessageListener {
    @Autowired
    private TeamsNotificationService teamsNotificationService;
    @Autowired
    private TenantConsumerService tenantConsumerService;
    @Autowired
    @Qualifier("sendThreadPool")
    private ThreadPoolTaskExecutor threadPool;
    @Autowired
    private KafkaListenerEndpointRegistry registry;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private TenantConfigUtils tenantConfigUtils;
    @Autowired
    private AdaptiveKafkaConsumer adaptiveKafkaConsumer;
    @Autowired
    private ServerStatusUtils serverStatusUtils;
    @Autowired
    private QuotaScheduler quotaScheduler;

    /*************************************************************************************************************************************/
    @KafkaListener(
            topics = "customs-topic-retry-2000",
            groupId = "multi-tenant-group",
            concurrency = "5",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void kafka1MinRetryListener(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException, InterruptedException {
        if (Objects.isNull(record)) {
            ack.acknowledge();
            return;
        }
        long currentTime = System.currentTimeMillis();
        long targetTime = Optional.ofNullable(record.headers().lastHeader("x-delay-until"))
                .map(h -> ByteBuffer.wrap(h.value()).getLong())
                .orElse(-1L);
        long remaining = targetTime - System.currentTimeMillis();
        log.info("[2000][{}] targetTime: {},  currentTime: {}", targetTime > currentTime, targetTime, currentTime);
        if (remaining > 0) {
            // 未到达目标时间，重新发送消息
            log.info("未到达目标时间，线程等待: {}", remaining);
            Thread.sleep(remaining); // 因为后续的消息目标时间一定比当前时间大，所以可以直接等待
        }
        tenantConsumerService.consume(record, 2, ack);
        ack.acknowledge();
    }

    @KafkaListener(
            topics = "customs-topic-retry-4000",
            groupId = "multi-tenant-group",
            concurrency = "5",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void kafka5MinRetryListener(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException, InterruptedException {
        if (Objects.isNull(record)) {
            ack.acknowledge();
            return;
        }
        long currentTime = System.currentTimeMillis();
        long targetTime = Optional.ofNullable(record.headers().lastHeader("x-delay-until"))
                .map(h -> ByteBuffer.wrap(h.value()).getLong())
                .orElse(-1L);
        long remaining = targetTime - System.currentTimeMillis();
        log.info("[4000][{}] targetTime: {},  currentTime: {}", targetTime > currentTime, targetTime, currentTime);
        if (remaining > 0) {
            // 未到达目标时间，重新发送消息
            log.info("未到达目标时间，线程等待: {}", remaining);
            Thread.sleep(remaining); // 因为后续的消息目标时间一定比当前时间大，所以可以直接等待
        }
        tenantConsumerService.consume(record, 3, ack);
        ack.acknowledge();
    }

    @KafkaListener(
            topics = "customs-topic.dlt",
            groupId = "dlt-alert-group",
            concurrency = "5",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void kafkaDltListener(ConsumerRecord<String, String> record, Acknowledgment ack) {
        if (Objects.isNull(record)) {
            ack.acknowledge();
            return;
        }
        log.error("死信队列消息出现: {}", record.value());
        teamsNotificationService.sendMessage("推送失败: " + record.value());
        ack.acknowledge();
    }

    /*************************************************************************************************************************************/

    @KafkaListener(
            topics = "customs-topic",
            groupId = "multi-tenant-group",
            concurrency = "3",
            containerFactory = "idleKafkaListenerContainerFactory",
            id = "pushListener"
    )
    public void kafkaListener(List<ConsumerRecord<String, String>> records, Acknowledgment ack) throws InterruptedException {
        if (records.isEmpty()) {
            ack.acknowledge();
            return;
        }

        Map<String, List<ConsumerRecord<String, String>>> recordsByTenant = records.stream()
                .collect(Collectors.groupingBy(ConsumerRecord::key));
        int totalMessages = records.stream().mapToInt(e -> 1).sum();
        CountDownLatch latchRequest = new CountDownLatch(totalMessages);
        CountDownLatch latch = new CountDownLatch(recordsByTenant.size());
        // todo 修改成配置获取目的地
//        int maxConsumeCount = serverStatusUtils.getMaxCapacity("customsServer") / recordsByTenant.size();

        recordsByTenant.forEach((tenantId, tenantRecords) -> {
            TenantConfigDomain tenantConfig = tenantConfigUtils.getTenantConfig(tenantId);
            if (tenantConfig == null) {
                // 租户不存在，消息处理不了，提交Offset后发送到DLQ
                tenantRecords.forEach(record -> {
                    kafkaProducer.sendMessage("customs-topic.dlt", record.value());
                    log.warn("租户配置不存在, 消息发送到DLQ: {}", record);
                    latchRequest.countDown();
                });

                latch.countDown();
                return;
            }
            int maxConsumeCount = adaptiveKafkaConsumer.getCurrentRoundQuota(tenantId);

//            log.info("租户[{}]当前可消费消息数: {}", tenantId, maxConsumeCount);
            tenantRecords.forEach(record -> threadPool.submit(() -> {
                int processed = tenantConfigUtils.incrementAndGetProcessed(tenantId);
//                log.info("租户[{}]正在处理第[{}]条数据: {}", tenantId, processed, record.value());
                if (processed > maxConsumeCount) {
                    // 超过限流数量，重新送回队列
//                    log.info("租户[{}]超过限流数量，第[{}]消息重新送回队列: {}", tenantId, processed, record.value());
                    kafkaProducer.sendMessage("customs-topic", tenantId, record.value());
                    latchRequest.countDown(); // 任务结束后 countDown
                    return;
                }
                try {
                    tenantConsumerService.consume(record, 1, ack);
                } catch (Exception e) {
                    // 说明在catch里面又出问题了，这里不再处理，直接提交死信队列
                    kafkaProducer.sendMessage("customs-topic.dlt", record.value());
                } finally {
                    latchRequest.countDown(); // 任务结束后 countDown
                }
            }));
            latch.countDown();
        });

        latch.await(); // 确保线程池已提交所有任务
        ack.acknowledge();

        latchRequest.await(); // 等待所有任务完成
        recordsByTenant.forEach((tenantId, tenantRecords) -> {
            tenantConfigUtils.resetProcessed(tenantId);
            adaptiveKafkaConsumer.adjustFetchCount(tenantId, tenantConfigUtils.getTenantConfig(tenantId));
            quotaScheduler.resetServerQuota();
            tenantConfigUtils.resetAllStatistics();
        });
        pauseListenerWithDelay("pushListener", Duration.ofSeconds(5));
    }

    // 暂停Listener并设定延迟自动恢复
    private void pauseListenerWithDelay(String listenerId, Duration duration) {
        MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
        if (listenerContainer != null && listenerContainer.isRunning()) {
            listenerContainer.pause();
            CompletableFuture.delayedExecutor(duration.getSeconds(), TimeUnit.SECONDS)
                    .execute(listenerContainer::resume);
        }
    }
}