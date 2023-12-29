package com.wong.store.cart.service;

/**
 * @author Jay Wong
 * @date 2023/12/29 20:21
 */
public interface CartService {
    // 添加sku到购物车
    void addToCart(Long skuId, Integer skuNum);
}
