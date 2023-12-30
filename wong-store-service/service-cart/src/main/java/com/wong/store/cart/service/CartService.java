package com.wong.store.cart.service;

import com.wong.store.model.entity.h5.CartInfo;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/29 20:21
 */
public interface CartService {
    // 添加sku到购物车
    void addToCart(Long skuId, Integer skuNum);

    // 查询购物车列表
    List<CartInfo> queryCartInfoList();

    // 根据skuId删除购物车中指定商品
    void deleteFromCartBySkuId(Long skuId);

    // 根据skuId更新购物车中指定商品的选中状态
    void updateCheckStatusBySkuId(Long skuId, Integer isChecked);

    // 统一更新购物车中全部商品的选中状态
    void updateAllCheckStatus(Integer isChecked);

    // 清空购物车
    void clearCart();
}
