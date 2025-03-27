package com.example.kafkademo.kafka.handler;

import org.springframework.stereotype.Service;

@Service
public interface MessageHandler {
    String handler(String message);
}
