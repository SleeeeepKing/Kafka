package com.example.kafkademo.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //发送消息方法
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}