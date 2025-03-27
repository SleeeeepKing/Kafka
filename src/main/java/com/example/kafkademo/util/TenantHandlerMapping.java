package com.example.kafkademo.util;

import com.example.kafkademo.kafka.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TenantHandlerMapping {
    private final Map<String, MessageHandler> handlerMap;

    @Autowired
    public TenantHandlerMapping(Map<String, MessageHandler> handlers) {
        handlerMap = new HashMap<>();
        handlerMap.put("tenantA", handlers.get("tenantAHandler"));
        handlerMap.put("tenantB", handlers.get("tenantBHandler"));
        handlerMap.put("tenantC", handlers.get("tenantCHandler"));
        handlerMap.put("customs", handlers.get("customsHandler"));
        //添加租户直接注册增加即可
    }

    public MessageHandler getHandler(String tenantId) {
        return handlerMap.get(tenantId);
    }
}