package com.wong.store.cart.controller;

import com.wong.store.cart.service.CartService;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
