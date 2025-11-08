package com.zotci.order.util;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class NacosConfigUtil {
    @Value("${spring.cloud.nacos.server-addr}")
    private String serverAddr;
    @Value("${spring.application.name}")
    private String appName;

    // Nacos 配置服务实例
    private ConfigService configService;

    // 初始化 Nacos 配置服务
    public ConfigService getConfigService() throws NacosException {
        if (configService == null) {
            Properties properties = new Properties();
            properties.put("serverAddr", serverAddr);
            configService = NacosFactory.createConfigService(properties);
        }
        return configService;
    }

    // 获取流控规则配置 ID
    public String getFlowRuleDataId() {
        System.out.println("getFlowRuleDataId" + serverAddr + appName + "*".repeat(8));
        return appName + "-flow-rules";
    }

    // 获取熔断规则配置 ID
    public String getDegradeRuleDataId() {
        return appName + "-degrade-rules";
    }

    // 通用组名
    public String getGroupId() {
        return "SENTINEL_GROUP";
    }
}