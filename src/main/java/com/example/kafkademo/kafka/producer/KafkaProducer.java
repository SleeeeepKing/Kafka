package com.example.kafkademo.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    //发送消息方法
    public void sendMessage(String topic, String tenantId, String message) {
        kafkaTemplate.send(topic, tenantId, message);
    }

    //发送消息方法
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    // 发送延迟消息
    public void sendDelayedMessage(String topic, Object payload, long delaySeconds) {
        long targetTimestamp = System.currentTimeMillis() + delaySeconds * 1000;
        kafkaTemplate.send(MessageBuilder
                .withPayload(payload)
                .setHeader("x-delay-until", ByteBuffer.allocate(Long.BYTES).putLong(targetTimestamp).array())
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build());
    }
}