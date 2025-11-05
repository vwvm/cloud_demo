package com.zotci.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@SpringBootTest
public class LoadBalancerTest {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Test
    void test() {
        ServiceInstance serviceProduct;
        for (int i = 0; i < 10; i++) {
            serviceProduct = loadBalancerClient.choose("service_product");
            System.out.println("choose =  " + serviceProduct.getHost() + ":" + serviceProduct.getPort());
        }
    }
}
