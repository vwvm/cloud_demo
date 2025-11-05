package com.zotci.product.config;

import com.zotci.product.entry.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service_product")
public interface ProductFeignClient {

    @GetMapping("/api/product")
    Product getProductById(@RequestParam("id") String id);
}
