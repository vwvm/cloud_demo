package com.zotci.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zotci.order.entry.Order;
import com.zotci.order.feign.ProductFeignClient;
import com.zotci.product.entry.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@SentinelResource(value = "createOrder")
@RequiredArgsConstructor
public class OrderService {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancerClient;
    private final ProductFeignClient productFeignClient;


    /**
     * 创建订单
     * @param userId 用户ID
     * @param productId 商品ID
     * @return Order对象
     */
    public Order createOrder(Long userId, Long productId) {
        // Product product = getProductFromRemoteWithLoadBalance(productId);
        Product product = productFeignClient.getProductById(productId);
        Order order = new Order();
        order.setId(0L);
        // 总金额
        BigDecimal multiply = product.getPrice().multiply(new BigDecimal(product.getNum()));
        order.setTotalAmount(multiply);
        order.setUserId(userId);
        order.setNickName("");
        order.setAddress("");
        // 远程调用
        order.setProductList(List.of(product));
        return order;
    }

    /**
     * 原始请求注册中心
     *
     * @param productId
     * @return
     */
    private Product getProductFromRemote(Long productId) {
        // 获取商品服务所在的所有机器IP + port
        List<ServiceInstance> instances = discoveryClient.getInstances("service_product");
        ServiceInstance serviceInstance = instances.get(0);
        // 构建URL
        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(serviceInstance.getHost())
                .port(serviceInstance.getPort())
                .path("/api/product")
                .queryParam("id", productId) // 自动编码特殊字符
                .toUriString();
        log.info("远程请求:{}", url);
        return restTemplate.getForObject(url, Product.class);
    }


    /**
     * 手动负载均衡
     *
     * @param productId
     * @return
     */
    private Product getProductFromRemoteWithLoadBalance(Long productId) {
        // 获取商品服务所在的所有机器IP + port
        ServiceInstance choose = loadBalancerClient.choose("service_product");
        // 构建URL
        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(choose.getHost())
                .port(choose.getPort())
                .path("/api/product")
                .queryParam("id", productId) // 自动编码特殊字符
                .toUriString();

        log.info("远程请求:{}", url);

        return restTemplate.getForObject(url, Product.class);
    }

    /**
     * 注解式负载均衡
     *
     * @param productId
     * @return
     */
    private Product getProductFromRemoteWithNotes(Long productId) {
        // 构建URL
        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("service_product")
                .path("/api/product")
                .queryParam("id", productId) // 自动编码特殊字符
                .toUriString();
        return restTemplate.getForObject(url, Product.class);
    }

}
