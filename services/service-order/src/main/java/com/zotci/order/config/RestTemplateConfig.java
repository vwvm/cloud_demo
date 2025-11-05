package com.zotci.order.config;

import feign.Logger;
import feign.RetryableException;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {


    /**
     * @return rest请求模板
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /**
     * @return 日志等级
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * @return Feign重试器
     */
    @Bean
    Retryer feignRetryer() {
        return new Retryer.Default();
    }

}
