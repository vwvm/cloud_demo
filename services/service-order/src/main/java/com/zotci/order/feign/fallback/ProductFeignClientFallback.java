package com.zotci.order.feign.fallback;
import java.math.BigDecimal;

import com.zotci.order.feign.ProductFeignClient;
import com.zotci.product.entry.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientFallback implements ProductFeignClient {

    @Override
    public Product getProductById(Long id) {
        System.out.println("兜底回调。。。");
        Product product = new Product();
        product.setId(0L);
        product.setPrice(new BigDecimal(10));
        product.setProductName("未知商品");
        product.setNum(10);

        return product;
    }
}
