package com.zotci.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // 被 Sentinel 保护的资源
    @GetMapping("/test")
    @SentinelResource(value = "testResource", fallback = "testFallback")
    public String test() {
        return "正常访问";
    }

    // 降级 fallback 方法
    public String testFallback() {
        return "触发降级";
    }
}