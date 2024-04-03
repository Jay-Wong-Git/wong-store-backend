package com.wong.store.feign.cart;

import com.wong.store.model.entity.h5.CartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/30 21:20
 */
@FeignClient("service-cart")
public interface CartFeignClient {
    // 获取购物车中所有选中的商品列表
    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    List<CartInfo> queryAllChecked();

    // 删除购物车中所有选中的商品
    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    void deleteChecked();
}
