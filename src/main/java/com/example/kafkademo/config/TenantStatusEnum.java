package com.example.kafkademo.config;

public enum TenantStatusEnum {
    /**
     * 1. NORMAL：正常 – 如果 successRate < 0.8，则进入 MONITOR，并将 fetchCount 适当下调
     * 2. MONITOR：监控状态 – 如果 successRate < 0.5，进入 DEGRADE，fetchCount ↓ 大幅降低 – 如果 successRate > 0.8，回到 NORMAL，fetchCount ↑ 恢复
     * 3. DEGRADE：严重降级（可将 fetchCount 降为1） – 如果 successRate >= 0.7，进入 RECOVER，fetchCount 从1向上慢慢加
     * 4. RECOVER：正在恢复 – 如果 successRate > 0.9 && fetchCount >=某阈值，如 100 => 回NORMAL – 如果 successRate < 0.5 => 回DEGRADE，重新收紧
     */
    NORMAL,
    MONITOR,
    DEGRADE,
    RECOVER
}
