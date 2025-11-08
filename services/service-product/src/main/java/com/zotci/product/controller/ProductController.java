package com.zotci.product.controller;

import com.zotci.product.entry.Product;
import com.zotci.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
@Tag(name = "商品API")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    @Operation(summary = "获取商品")
    public Product getProduct(@RequestParam("id") Long productId) {
        log.info("productId:{}", productId);
        // try {
        //     Thread.sleep(6000);
        // } catch (InterruptedException e) {
        //     throw new RuntimeException(e);
        // }
        return productService.getProduct(productId);
    }
}
