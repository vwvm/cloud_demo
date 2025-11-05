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
@RequestMapping("/api")
@Tag(name = "订单API")
public class OrderController {

    private final OrderService orderService;
    private final Properties properties;

    @GetMapping("/getConfig")
    @Operation(summary = "获取配置")
    public String getConfig(){
        return properties.toString();
    }


    @GetMapping("/createOrder")
    @Operation(summary = "获取商品")
    public Order createOrder(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        return orderService.createOrder(userId, productId);
    }
}
