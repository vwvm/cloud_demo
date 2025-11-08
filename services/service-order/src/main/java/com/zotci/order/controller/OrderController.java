package com.zotci.order.controller;

import com.zotci.order.config.Properties;
import com.zotci.order.entry.Order;
import com.zotci.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
@Tag(name = "订单API")
public class OrderController {

    private final OrderService orderService;
    private final Properties properties;

    @GetMapping("/getConfig")
    @Operation(summary = "获取配置")
    public String getConfig() {
        return properties.toString();
    }


    @GetMapping("/createOrder")
    @Operation(summary = "普通创建订单")
    public Order createOrder(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        System.out.println("发送请求了".repeat(3));
        return orderService.createOrder(userId, productId);
    }

    @GetMapping("/secKillOrder")
    @Operation(summary = "秒杀创建订单")
    public Order secKillOrder(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        return orderService.createOrder(userId, productId);
    }

    @GetMapping("/writeDb")
    @Operation(summary = "写数据库")
    public String writeDb() {
        return "写数据库";
    }

    @GetMapping("/readDb")
    @Operation(summary = "读数据库")
    public String readDb() {
        return "读数据库";
    }
}
