package com.example.kafkademo.scheduler;

import com.example.kafkademo.util.ServerStatusUtils;
import com.example.kafkademo.util.TenantConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CountScheduler {
    @Autowired
    private TenantConfigUtils tenantConfigUtils;
    @Autowired
    private ServerStatusUtils serverStatusUtils;

//    @Scheduled(fixedRate = 5000)
//    public void resetServerQuota() {
//        String serverName = "customsServer";
//        serverStatusUtils.resetStatistics(serverName);
//    }

//    @Scheduled(fixedRate = 5000)
//    public void resetTenantStatistics() {
//        tenantConfigUtils.resetAllStatistics();
//    }
}
