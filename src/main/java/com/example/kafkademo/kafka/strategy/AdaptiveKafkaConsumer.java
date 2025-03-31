package com.example.kafkademo.kafka.strategy;

import com.example.kafkademo.config.TenantConfigDomain;
import com.example.kafkademo.config.TenantStatusEnum;
import com.example.kafkademo.config.CustomsServerStatus;
import com.example.kafkademo.util.ServerStatusUtils;
import com.example.kafkademo.util.TenantConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@EnableScheduling
public class AdaptiveKafkaConsumer {
    @Autowired
    private TenantConfigUtils tenantConfigUtils;
    @Autowired
    private ServerStatusUtils serverStatusUtils;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final int MIN_TOTAL_THRESHOLD = 5; // 至少推送20条数据才进行调整


    // 定时任务：每分钟调用一次统计和调整fetch count：
    @Scheduled(fixedRate = 5000) //每分钟调用一次（推荐）
    public void adjustAllTenantsFetchCount() {
        List<String> tenantIds = tenantConfigUtils.getAllTenantIds(); //也可以从配置或Redis动态加载

        for (String tenantId : tenantIds) {
            TenantConfigDomain config = tenantConfigUtils.getTenantConfig(tenantId);
            log.info("租户[{}]: {}", tenantId, config);
            if (config != null) {
                adjustFetchCount(tenantId, config);
            }
        }
    }

    /**
     * 动态调整fetch count，定时器调用，每分钟一次统计数据进行调整
     */
    private void adjustFetchCount(String tenantId, TenantConfigDomain config) {
        log.info("开始动态调整租户[{}]", tenantId);
        if (config == null) return;

        // 至少推送多少条数据才进行调整
        double successRate = config.getTotal() >= MIN_TOTAL_THRESHOLD ? (double) config.getSuccess() / config.getTotal() : 1;
        if (tenantConfigUtils.getTenantState(tenantId) == TenantStatusEnum.DEGRADE) {
            successRate = 0;
        }
        int newFetchCount = config.getFetchCount();
        TenantStatusEnum newState = config.getState();
        // todo 改成从配置文件或数据库加载
        CustomsServerStatus serverStatus = serverStatusUtils.getServerStatus("customsServer");

        if (serverStatus.getIsAlive() == 0) {
            newFetchCount = 1;
            newState = TenantStatusEnum.DEGRADE;
        } else if (config.getTotal() >= MIN_TOTAL_THRESHOLD) {
            if (successRate < 0.3) {
                newFetchCount = 1;
                newState = TenantStatusEnum.DEGRADE;
            } else if (successRate < 0.6) {
                newFetchCount = Math.max((int) Math.round((newFetchCount * 0.8)), config.getMinFetchCount());
                newState = TenantStatusEnum.MONITOR;
            } else if (successRate < 0.8) {
                newFetchCount = Math.min((int) Math.round((newFetchCount * 1.05)), config.getMaxFetchCount());
                newState = TenantStatusEnum.RECOVER;
            } else {
                newFetchCount = Objects.equals(newState, TenantStatusEnum.DEGRADE) ? config.getMinFetchCount() : Math.min((int) Math.round((newFetchCount * 1.15)), config.getMaxFetchCount());
                newState = TenantStatusEnum.NORMAL;
            }
        }
//        if (serverStatus.getIsAlive() == 0) {
//            newState = TenantStatusEnum.DEGRADE;
//        } else if (successRate < 0.5) {
//            newState = TenantStatusEnum.DEGRADE;
//        } else {
//            newState = TenantStatusEnum.NORMAL;
//        }
        if (config.getTotal() >= MIN_TOTAL_THRESHOLD || serverStatus.getIsAlive() == 0) {
            config.setFetchCount(newFetchCount);
            config.setState(newState);
        }

        tenantConfigUtils.updateFetchCount(tenantId, config.getFetchCount());
        tenantConfigUtils.updateStatus(tenantId, config.getState());
        // 重置计数，进入下一时间窗口
        if (config.getTotal() >= MIN_TOTAL_THRESHOLD + 20) {
            config.setTotal(0);
            config.setSuccess(0);
        }
        log.info(
                "租户[{}],总推送数：{}, 成功数:{}, 成功率： {}%, 下一次期望抓取量: {}, 新状态: {}",
                tenantId, config.getTotal(), config.getSuccess(), successRate * 100, newFetchCount, newState
        );
    }

    /**
     * 获取当前批次可用额度
     */
    public int getCurrentRoundQuota(String tenantId) {
        TenantConfigDomain config = tenantConfigUtils.getTenantConfig(tenantId);
        // 计算本轮可用额度
        int minQuota = config.getMinFetchCount();
        int requiredQuota = config.getFetchCount() <= minQuota ? 0 : config.getFetchCount() - minQuota;
        int leftOver = acquireLeftoverQuota("server:customsServer:status", requiredQuota);
//        int leftOver = 1;
        boolean isDown = Objects.equals(serverStatusUtils.getServerStatus("customsServer").getIsAlive(), 0) || Objects.equals(config.getState(), TenantStatusEnum.DEGRADE);
        return isDown ? 1 : minQuota + leftOver; //动态竞争quota后得出的额度
    }


    // 原子操作，从Redis安全地竞争额度
    private int acquireLeftoverQuota(String hashKey, int requestAmount) {
        // Lua脚本 (可直接内联，也可独立文件后加载成字符串)
        String script = """
                local quotaStr = redis.call("HGET", KEYS[1], "quota")
                    if not quotaStr then
                        return 0
                    end
                    local quota = tonumber(quotaStr)
                    if not quota or quota <= 0 then
                        return 0
                    end
                    local want = tonumber(ARGV[1])
                    -- 新增判断：如果 want 是 nil 或 <= 0，则直接返回
                    if not want or want <= 0 then
                        return 0
                    end
                    local toTake = math.min(quota, want)
                    redis.call("HINCRBY", KEYS[1], "quota", -toTake)
                    return toTake
                """;

        // 构造RedisScript对象, 指定返回类型为Long
        RedisScript<Long> redisScript = RedisScript.of(script, Long.class);

        // 执行脚本
        Long result = redisTemplate.execute(
                redisScript,
                List.of(hashKey),   // 传入KEYS
                String.valueOf(requestAmount)  // 传入ARGV
        );

        // 如果result为null或其他情况，返回0
        return result == null ? 0 : result.intValue();
    }
}