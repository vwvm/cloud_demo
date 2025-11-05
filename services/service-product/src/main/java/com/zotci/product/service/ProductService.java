package com.zotci.product.service;

import com.zotci.product.entry.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {
    public Product getProduct(Long productId) {

        return new Product(productId, new BigDecimal(98), "", 2);
    }
}
