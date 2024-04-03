package com.wong.store.cart.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wong.store.cart.service.CartService;
import com.wong.store.feign.product.ProductFeignClient;
import com.wong.store.model.entity.h5.CartInfo;
import com.wong.store.model.entity.product.ProductSku;
import com.wong.store.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        // 获取cartKey
        String cartKey = getCartKey();
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
            cartInfo.setUserId(getUserId());
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        // 将商品数据存储到购物车中
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSONObject.toJSONString(cartInfo));
    }

    /**
     * 查询购物车列表
     *
     * @return 购物车列表
     */
    @Override
    public List<CartInfo> queryCartInfoList() {
        // 获取cartKey
        String cartKey = getCartKey();
        // 从redis中获取数据
        List<Object> cartInfoObjList = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtils.isEmpty(cartInfoObjList)) {
            return cartInfoObjList
                    .stream()
                    .map(cartInfoObj -> JSONObject.parseObject(cartInfoObj.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 根据skuId删除购物车中指定商品
     *
     * @param skuId skuId
     */
    @Override
    public void deleteFromCartBySkuId(Long skuId) {
        // 从redis中删除数据
        redisTemplate.opsForHash().delete(getCartKey(), String.valueOf(skuId));
    }

    /**
     * 根据skuId更新购物车中指定商品的选中状态
     *
     * @param skuId     skuId
     * @param isChecked 选中状态 1:选中 0:取消
     */
    @Override
    public void updateCheckStatusBySkuId(Long skuId, Integer isChecked) {
        Object cartInfoObj = redisTemplate.opsForHash().get(getCartKey(), String.valueOf(skuId));
        if (cartInfoObj != null) {
            CartInfo cartInfo = JSONObject.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(getCartKey(), String.valueOf(skuId), JSONObject.toJSONString(cartInfo));
        }
    }

    /**
     * 统一更新购物车中全部商品的选中状态
     *
     * @param isChecked 选中状态 1:选中 0:取消
     */
    @Override
    public void updateAllCheckStatus(Integer isChecked) {
        List<Object> cartInfoObjList = redisTemplate.opsForHash().values(getCartKey());
        if (!CollectionUtils.isEmpty(cartInfoObjList)) {
            cartInfoObjList
                    .stream()
                    .map(cartInfoObj -> {
                        CartInfo cartInfo = JSONObject.parseObject(cartInfoObj.toString(), CartInfo.class);
                        cartInfo.setIsChecked(isChecked);
                        return cartInfo;
                    })
                    .forEach(cartInfo -> redisTemplate.opsForHash().put(getCartKey(), String.valueOf(cartInfo.getSkuId()), JSONObject.toJSONString(cartInfo)));
        }
    }

    /**
     * 清空购物车
     */
    @Override
    public void clearCart() {
        redisTemplate.delete(getCartKey());
    }

    /**
     * 获取购物车中选中的商品列表
     *
     * @return 选中的商品列表
     */
    @Override
    public List<CartInfo> queryAllChecked() {
        List<Object> cartInfoObjList = redisTemplate.opsForHash().values(getCartKey());
        if (!CollectionUtils.isEmpty(cartInfoObjList)) {
            return cartInfoObjList
                    .stream()
                    .map(cartInfoObj -> JSONObject.parseObject(cartInfoObj.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 删除购物车中选中的商品
     */
    @Override
    public void deleteChecked() {
        List<Object> cartInfoObjList = redisTemplate.opsForHash().values(getCartKey());
        if (!CollectionUtils.isEmpty(cartInfoObjList)) {
            cartInfoObjList
                    .stream()
                    .map(cartInfoObj -> JSONObject.parseObject(cartInfoObj.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete(getCartKey(), String.valueOf(cartInfo.getSkuId())));
        }
    }

    /**
     * 生成cartKey
     *
     * @return cartKey
     */
    private String getCartKey() {
        return "user:cart:" + getUserId();
    }

    /**
     * 从LocalThread中获取userId
     *
     * @return userId
     */
    private Long getUserId() {
        return AuthContextUtil.getUserInfo().getId();
    }
}
