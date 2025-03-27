package com.example.kafkademo.kafka.handler;

import org.springframework.stereotype.Component;

@Component("tenantCHandler")
public class TenantCHandler implements MessageHandler {
    @Override
    public String handler(String message) {
        // 租户B的处理逻辑
        System.out.println("TenantC处理消息: " + message);
        return "200";
    }
}