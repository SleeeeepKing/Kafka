package com.example.kafkademo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomsServerStatus {
    private Integer total;
    private Integer success;
    private Integer isAlive;
    private Integer capacity;
    private Integer quota;
}
