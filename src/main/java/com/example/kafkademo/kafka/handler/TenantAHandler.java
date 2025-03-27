package com.example.kafkademo.kafka.handler;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("tenantAHandler")
public class TenantAHandler implements MessageHandler {
    @Override
    public String handler(String message) {
        // 租户A的处理逻辑
        System.out.println("TenantA处理消息: " + message);
        // 可调用第三方接口或业务逻辑
        return "400";
    }
}