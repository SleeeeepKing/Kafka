package com.example.kafkademo.kafka.strategy;

import com.example.kafkademo.config.TenantConfigDomain;
import com.example.kafkademo.config.TenantStatusEnum;
import com.example.kafkademo.config.CustomsServerStatus;
import com.example.kafkademo.util.ServerStatusUtils;
import com.example.kafkademo.util.TenantConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AdaptiveKafkaConsumer {
    @Autowired
    private TenantConfigUtils tenantConfigUtils;
    @Autowired
    private ServerStatusUtils serverStatusUtils;

    private static final int MIN_TOTAL_THRESHOLD = 100;


    // 定时任务：每分钟调用一次统计和调整fetch count：
    @Scheduled(fixedRate = 60000) //每分钟调用一次（推荐）
    public void adjustAllTenantsFetchCount() {
        List<String> tenantIds = tenantConfigUtils.getAllTenantIds(); //也可以从配置或Redis动态加载

        for (String tenantId : tenantIds) {
            TenantConfigDomain config = tenantConfigUtils.getTenantConfig(tenantId);
            if (config != null) {
                adjustFetchCount(tenantId, config);
            }
        }
    }

    /**
     * 动态调整fetch count，定时器调用，每分钟一次统计数据进行调整
     */
    private void adjustFetchCount(String tenantId, TenantConfigDomain config) {
        if (config == null) return;

        double successRate = config.getTotal() >= MIN_TOTAL_THRESHOLD ? (double) config.getSuccess() / config.getTotal() : 1;

        int newFetchCount = config.getFetchCount();
        TenantStatusEnum newState;
        CustomsServerStatus serverStatus = serverStatusUtils.getServerStatus("serverA");

        if (serverStatus.getIsAlive() == 0) {
            newFetchCount = 1;
            newState = TenantStatusEnum.DEGRADE;
        } else if (successRate < 0.3) {
            newFetchCount = Math.max(newFetchCount / 2, config.getMinFetchCount());
            newState = TenantStatusEnum.DEGRADE;
        } else if (successRate < 0.6) {
            newFetchCount = Math.max((int) (newFetchCount * 0.9), config.getMinFetchCount());
            newState = TenantStatusEnum.MONITOR;
        } else if (successRate < 0.8) {
            newFetchCount = Math.min((int) (newFetchCount * 1.05), config.getMaxFetchCount());
            newState = TenantStatusEnum.RECOVER;
        } else {
            newFetchCount = Math.min((int) (newFetchCount * 1.15), config.getMaxFetchCount());
            newState = TenantStatusEnum.NORMAL;
        }

        config.setFetchCount(newFetchCount);
        config.setState(newState);

        tenantConfigUtils.updateTenantConfig(tenantId, config);
        // 重置计数，进入下一时间窗口
        config.setTotal(0);
        config.setSuccess(0);

        log.info(
                "Tenant: {}, SuccessRate: {}, Adjusted fetchCount: {}, State: {}",
                tenantId, successRate * 100, newFetchCount, newState
        );
    }
}