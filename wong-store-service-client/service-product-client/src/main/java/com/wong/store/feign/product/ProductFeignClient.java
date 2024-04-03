package com.wong.store.feign.product;

import com.wong.store.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Jay Wong
 * @date 2023/12/29 21:40
 */
@FeignClient("service-product")
public interface ProductFeignClient {
    @GetMapping("/api/product/querySkuBySkuId/{skuId}")
    ProductSku querySkuBySkuId(@PathVariable Long skuId);
}
