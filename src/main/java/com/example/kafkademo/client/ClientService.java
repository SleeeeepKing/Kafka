package com.example.kafkademo.client;

import com.example.kafkademo.kafka.comsumer.dto.ClientDTO;
import com.example.kafkademo.kafka.producer.KafkaProducer;
import com.example.kafkademo.util.JacksonSerializeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientService {
    @Autowired
    private KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void processTenantMessage(ClientDTO clientDTO) throws JsonProcessingException {
        // 进行申报DTO转换
        //...
        // 从message中获取tenantId
        // 发送消息到kafka
        String msg = JacksonSerializeUtil.serialize(clientDTO);
        sendMessageWithKey("client-topic", clientDTO.getTenantId(), msg);
    }

    public void processServerMessage(ClientDTO clientDTO) throws JsonProcessingException {
        // 进行result DTO转换
        //...
        // 发送消息到kafka
        sendMessage("server-topic", JacksonSerializeUtil.serialize(clientDTO));
    }

    public void sendMessageWithKey(String topic, String tenantId, String message) {
        kafkaProducer.sendMessage(topic, tenantId, message);
        log.info("发送消息到kafka成功:{}", message);
    }

    public void sendMessage(String topic, String message) {
        kafkaProducer.sendMessage(topic, message);
        log.info("发送消息到kafka成功:{}", message);
    }
}
