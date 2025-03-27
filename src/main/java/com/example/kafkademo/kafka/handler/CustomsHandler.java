package com.example.kafkademo.kafka.handler;

import org.springframework.stereotype.Component;

@Component("customsHandler")
public class CustomsHandler implements MessageHandler {
    @Override
    public String handler(String message) {
        // 租户B的处理逻辑
        System.out.println("Customs callback处理消息: " + message);
        return "200";
    }
}