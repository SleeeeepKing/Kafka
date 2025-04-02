package com.example.kafkademo.config.dto;

import com.example.kafkademo.config.enums.TenantStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantConfigDomain {
    private Integer total; // 发送总数，每个时间窗口重置为0
    private Integer success; // 成功数， 每个时间窗口重置为0
    private TenantStatusEnum state; // 租户状态
    private Integer leftOver; // 剩余可处理额度（每消费一条消息就减一条，最小值为0），每个时间窗口重新分配
    private Integer detection; // 状态为DOWN时的连续成功次数，到达5则恢复NORMAL，在到达5次或者成功中断则计数清空
    private Integer capacity; // 服务器容量
}
