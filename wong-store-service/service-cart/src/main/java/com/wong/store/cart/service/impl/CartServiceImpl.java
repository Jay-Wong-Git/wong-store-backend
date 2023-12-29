package com.wong.store.cart.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wong.store.cart.service.CartService;
import com.wong.store.feign.product.ProductFeignClient;
import com.wong.store.model.entity.h5.CartInfo;
import com.wong.store.model.entity.product.ProductSku;
import com.wong.store.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Jay Wong
 * @date 2023/12/29 20:22
 */
@Service
public class CartServiceImpl implements CartService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private ProductFeignClient productFeignClient;

    /**
     * 添加sku到购物车
     *
     * @param skuId  skuId
     * @param skuNum sku数量
     */
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        // 获取当前登录用户的id
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = "user:cart:" + userId;
        // 获取缓存对象
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        CartInfo cartInfo = null;
        if (cartInfoObj != null) {
            // 如果购物车中有该商品，则商品数量相加
            cartInfo = JSONObject.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            // 购物车中的商品默认为选中状态
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {
            // 当购物车中没用该商品的时候，则直接添加到购物车
            cartInfo = new CartInfo();
            // 远程调用，根据skuId获取ProductSku对象
            ProductSku productSku = productFeignClient.querySkuBySkuId(skuId);
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        // 将商品数据存储到购物车中
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSONObject.toJSONString(cartInfo));
    }
}
