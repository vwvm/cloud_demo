package com.zotci.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "order") // 批量绑定配置，无需@RefreshScope就能实现自动刷新
public class Properties {
    String timeout;
    String dbUrl;
}
