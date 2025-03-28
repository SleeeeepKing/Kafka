package com.example.kafkademo.kafka.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("customsHandler")
@Slf4j
public class CustomsHandler implements MessageHandler {
    @Override
    public String handler(String message) {
        // 租户B的处理逻辑
        log.info("Customs callback处理消息: " + message);
        return "200";
    }
}