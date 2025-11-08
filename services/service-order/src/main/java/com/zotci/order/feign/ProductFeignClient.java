package com.zotci.order.feign;

import com.zotci.order.feign.fallback.ProductFeignClientFallback;
import com.zotci.product.entry.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-product", path = "/api/product", fallback = ProductFeignClientFallback.class)
public interface ProductFeignClient {

    @GetMapping()
    Product getProductById(@RequestParam("id") Long id);
}
