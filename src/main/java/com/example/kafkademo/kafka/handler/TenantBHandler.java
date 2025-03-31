package com.example.kafkademo.kafka.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("tenantBHandler")
@Slf4j
public class TenantBHandler implements MessageHandler {
    @Override
    public String handler(String message) {
        // 租户B的处理逻辑
        log.info("TenantB处理消息: " + message);
        if (Math.random() < 0.5) {
            return "200";
        } else {
            return "500";
        }
    }
}