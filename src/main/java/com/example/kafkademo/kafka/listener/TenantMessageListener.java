package com.example.kafkademo.kafka.listener;

import com.example.kafkademo.kafka.comsumer.TenantConsumerService;
import com.example.kafkademo.util.TeamsNotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TenantMessageListener {
    @Autowired
    private TeamsNotificationService teamsNotificationService;
    @Autowired
    private TenantConsumerService tenantConsumerService;

//    @RetryableTopic(
//            attempts = "3",
//            backoff = @Backoff(delay = 2000, multiplier = 2),
//            autoCreateTopics = "true",
//            dltTopicSuffix = ".dlt"
//    )
//    @KafkaListener(
//            topics = "client-topic",
//            groupId = "multi-tenant-group",
//            concurrency = "5",
//            containerFactory = "idleKafkaListenerContainerFactory" // 指明使用上面配置的Factory，每五秒poll一次消息队列
//    )
//    public void kafkaListener(ConsumerRecord<String, String> record) throws JsonProcessingException {
//        tenantConsumerService.consume(record);
//    }



    @KafkaListener(
            topics = "client-topic",
            groupId = "multi-tenant-group",
            concurrency = "5",
            containerFactory = "idleKafkaListenerContainerFactory", // 指明使用上面配置的Factory，每五秒poll一次消息队列
            id = "pushListener" // 配置Listener ID用于暂停和恢复
    )
    public void kafkaListener(List<ConsumerRecord<String, String>> records, Acknowledgment ack) throws JsonProcessingException {
        tenantConsumerService.consumeByBatch(records);
    }

    // 消费死信队列消息并发出预警
    @KafkaListener(topics = "client-topic.dlt", groupId = "dlt-alert-group", containerFactory = "kafkaListenerContainerFactory")
    public void onDeadLetterMessage(ConsumerRecord<String, String> record) {
        log.error("死信队列消息出现: {}", record.value());
        teamsNotificationService.sendMessage("推送失败: " + record.value());
        //此处可发起报警操作(例如邮件或企业微信通知管理员)
    }


}