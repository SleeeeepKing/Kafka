package com.example.kafkademo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantConfigDomain {
    private Integer total; // 发送总数
    private Integer success; // 成功数
    private TenantStatusEnum state; // 租户状态
    private Integer fetchCount; // 每次拉取数量 = 1 或者 (minFetchCount + leftOver)
    private Integer minFetchCount; // 最小拉取数量
    private Integer maxFetchCount; // 最大拉取数量
    private Integer leftOver; // 竞争到的剩余额度
}
