package com.example.kafkademo.kafka.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("tenantAHandler")
@Slf4j
public class TenantAHandler implements MessageHandler {
    @Override
    public String handler(String message) {
        // 租户A的处理逻辑
        log.info("TenantA处理消息: " + message);
        // 可调用第三方接口或业务逻辑
        // 随机60%返回200 40%返回500
        if (Math.random() < 0.3) {
            return "200";
        } else {
            return "500";
        }
    }
}