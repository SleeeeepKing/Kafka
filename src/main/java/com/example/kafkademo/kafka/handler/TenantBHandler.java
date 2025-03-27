package com.example.kafkademo.kafka.handler;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("tenantBHandler")
public class TenantBHandler implements MessageHandler {
    @Override
    public String handler(String message) {
        // 租户B的处理逻辑
        System.out.println("TenantB处理消息: " + message);
        return "200";
    }
}