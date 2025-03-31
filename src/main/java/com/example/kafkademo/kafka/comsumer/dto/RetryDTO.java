package com.example.kafkademo.kafka.comsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetryDTO {
    private String tenantId;
    private String message;
    private String retryTime;
}
