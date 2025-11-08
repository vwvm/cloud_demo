package com.zotci.order.util;

import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SentinelRuleSyncToNacos implements InitFunc {

    private final NacosConfigUtil nacosConfigUtil;

    // 初始化时注册规则写入数据源（Sentinel -> Nacos）
    @Override
    public void init() throws Exception {
        // 流控规则反向同步
        WritableDataSource<List<FlowRule>> flowRuleDataSource = new WritableDataSource<List<FlowRule>>() {
            @Override
            public void write(List<FlowRule> rules) throws Exception {
                // 1. 将规则转为 JSON
                String ruleJson = JSON.toJSONString(rules);
                // 2. 写入 Nacos
                ConfigService configService = nacosConfigUtil.getConfigService();
                configService.publishConfig(
                        nacosConfigUtil.getFlowRuleDataId(),
                        nacosConfigUtil.getGroupId(),
                        ruleJson
                );
            }

            @Override
            public void close() throws Exception {
            }
        };

        // 注册数据源到 Sentinel，监听规则变更
        WritableDataSourceRegistry.registerFlowDataSource(flowRuleDataSource);
    }

    // 启动时初始化（替代 Sentinel 的 InitFunc 自动扫描，确保 Spring 注入生效）
    @EventListener(ContextRefreshedEvent.class)
    public void initAfterSpring() throws Exception {
        this.init();
    }
}