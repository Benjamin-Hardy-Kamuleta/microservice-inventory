package com.hkbusiness.microserviceinventory.config.feign;

import com.hkbusiness.microserviceinventory.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("MICROSERVICE-PRODUCT")
public interface ProductClient {
    @GetMapping("/api/products")
    public List<Product> products();
}
