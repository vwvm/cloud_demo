package com.zotci.product;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductApplicationTest {
    @Autowired
    DiscoveryClient discoveryClient;

    @Test
    void discoveryClientTest() {
        for (String serviceName : discoveryClient.getServices()) {
            System.out.print("server_name:" + serviceName);
            val instances = discoveryClient.getInstances(serviceName);
            instances.forEach(instance -> {
                System.out.print(" host:" + instance.getHost());
                System.out.println(" port:" + instance.getPort());
            });
            // System.out.println("*".repeat(32));
        }
    }

}