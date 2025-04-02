package com.example.kafkademo.kafka.comsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private String tenantId;
    private String url;
    private String message;
}
