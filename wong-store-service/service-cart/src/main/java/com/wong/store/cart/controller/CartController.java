package com.wong.store.cart.controller;

import com.wong.store.cart.service.CartService;
import com.wong.store.model.entity.h5.CartInfo;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/29 20:21
 */
@Tag(name = "购物车接口")
@RestController
@RequestMapping("/api/order/cart")
public class CartController {
    @Resource
    private CartService cartService;

    /**
     * 添加sku到购物车接口
     *
     * @param skuId  skuId
     * @param skuNum sku数量
     * @return 不返回数据
     */
    @Operation(summary = "添加sku到购物车接口")
    @GetMapping("/auth/addToCart/{skuId}/{skuNum}")
    public Result<Void> addToCart(
            @Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable("skuId") Long skuId,
            @Parameter(name = "skuNum", description = "数量", required = true) @PathVariable("skuNum") Integer skuNum) {
        cartService.addToCart(skuId, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 查询购物车列表接口
     *
     * @return 购物车列表
     */
    @Operation(summary = "查询购物车列表接口")
    @GetMapping("/auth/cartList")
    public Result<List<CartInfo>> queryCartInfoList() {
        List<CartInfo> cartInfoList = cartService.queryCartInfoList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据skuId删除购物车中指定商品接口
     *
     * @param skuId skuId
     * @return 不返回数据
     */
    @Operation(summary = "删除购物车中指定商品接口")
    @DeleteMapping("/auth/deleteCart/{skuId}")
    public Result<Void> deleteFromCartBySkuId(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable("skuId") Long skuId) {
        cartService.deleteFromCartBySkuId(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据skuId更新购物车中指定商品的选中状态接口
     *
     * @param skuId     skuId
     * @param isChecked 商品是否被选中 1:选中 0:取消
     * @return 不返回数据
     */
    @Operation(summary = "根据skuId更新购物车中指定商品的选中状态接口")
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result<Void> updateCheckStatusBySkuId(
            @Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable(value = "skuId") Long skuId,
            @Parameter(name = "isChecked", description = "是否选中 1:选中 0:取消选中", required = true) @PathVariable(value = "isChecked") Integer isChecked) {
        cartService.updateCheckStatusBySkuId(skuId, isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 统一更新购物车中全部商品的选中状态接口
     *
     * @param isChecked 商品是否被选中 1:选中 0:取消
     * @return 不返回数据
     */
    @Operation(summary = "统一更新购物车中全部商品的选中状态接口")
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result<Void> updateAllCheckStatus(@Parameter(name = "isChecked", description = "是否选中 1:选中 0:取消选中", required = true) @PathVariable(value = "isChecked") Integer isChecked) {
        cartService.updateAllCheckStatus(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 清空购物车接口
     *
     * @return 不返回数据
     */
    @Operation(summary = "清空购物车接口")
    @GetMapping("/auth/clearCart")
    public Result<Void> clearCart() {
        cartService.clearCart();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 用于远程调用，获取购物车中选中的商品列表接口
     *
     * @return 选中的商品列表
     */
    @Operation(summary = "获取购物车中选中的商品列表接口")
    @GetMapping(value = "/auth/getAllCkecked")
    public List<CartInfo> queryAllChecked() {
        return cartService.queryAllChecked();
    }

    /**
     * 用于远程调用，删除购物车中选中的商品接口
     */
    @Operation(summary = "删除购物车中选中的商品接口")
    @GetMapping(value = "/auth/deleteChecked")
    public void deleteChecked() {
        cartService.deleteChecked();
    }
}
