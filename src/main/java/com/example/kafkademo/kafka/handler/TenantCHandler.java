package com.example.kafkademo.kafka.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("tenantCHandler")
@Slf4j
public class TenantCHandler implements MessageHandler {
    @Override
    public String handler(String message) {
        // 租户B的处理逻辑
        log.info("TenantC处理消息: " + message);
        return "200";
    }
}