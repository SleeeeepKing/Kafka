package com.example.kafkademo.config.enums;

public enum TenantStatusEnum {
    /**
     * 1. NORMAL：正常 – 如果 successRate < 0.8，则进入 MONITOR，并将 fetchCount 适当下调
     * 2. MONITOR：监控状态 – 如果 successRate < 0.6，进入 DEGRADE，fetchCount ↓ 大幅降低 – 如果 successRate > 0.8，回到 NORMAL，fetchCount ↑ 恢复
     * 3. DEGRADE：严重降级 – 如果 successRate < 0.3，进入 DOWN，fetchCount 变为 1
     * 4. DOWN：服务关闭 – 如果连续5次探测成功，恢复NORMAL
     */
    NORMAL, // 100%
    MONITOR, // 50%
    DEGRADE, // 30%
    DOWN // 1
}
