package com.example.kafkademo.kafka.listener;

import com.example.kafkademo.exception.InternalServerException;
import com.example.kafkademo.kafka.comsumer.TenantConsumerService;
import com.example.kafkademo.kafka.producer.KafkaProducer;
import com.example.kafkademo.util.TeamsNotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TenantMessageListener {
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

    @KafkaListener(
            topics = "client-topic-retry-2000",
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
            topics = "client-topic-retry-4000",
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
            topics = "client-topic.dlt",
            groupId = "dlt-alert-group",
            concurrency = "5",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void kafkaDltListener(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException {
        if (Objects.isNull(record)) {
            ack.acknowledge();
            return;
        }
        log.error("死信队列消息出现: {}", record.value());
//        teamsNotificationService.sendMessage("推送失败: " + record.value());
        ack.acknowledge();
    }

    @KafkaListener(
            topics = "client-topic",
            groupId = "multi-tenant-group",
            concurrency = "5",
            containerFactory = "idleKafkaListenerContainerFactory", // 指明使用上面配置的Factory，每五秒poll一次消息队列
            id = "pushListener" // 配置Listener ID用于暂停和恢复
    )
    public void kafkaListener(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        if (records.isEmpty()) {
            ack.acknowledge();
            return;
        }

        records.forEach(record -> threadPool.submit(() -> {
            try {
                tenantConsumerService.consume(record, 1, ack);
            } catch (JsonProcessingException e) {
                throw new InternalServerException(e);
            }
        }));

        // 提交最大offset+1（快速提交，推进offset）
        ack.acknowledge();
        // 暂停当前Listener，延迟一定时间后自动恢复
        pauseListenerWithDelay("pushListener", Duration.ofSeconds(5));
    }

    // 暂停Listener并设定延迟自动恢复
    private void pauseListenerWithDelay(String listenerId, Duration duration) {
        Objects.requireNonNull(registry.getListenerContainer(listenerId)).pause();
        CompletableFuture.delayedExecutor(duration.getSeconds(), TimeUnit.SECONDS)
                .execute(() -> Objects.requireNonNull(registry.getListenerContainer(listenerId)).resume());
    }

}