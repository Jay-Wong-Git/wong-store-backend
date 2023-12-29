package com.wong.store.product.controller;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.dto.h5.ProductSkuDto;
import com.wong.store.model.entity.product.ProductSku;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.h5.ProductItemVo;
import com.wong.store.product.service.ProductService;
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
 * @date 2023/12/28 18:21
 */
@Tag(name = "商品接口")
@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    @Resource
    private ProductService productService;

    /**
     * @param page          当前页
     * @param limit         每页显示条数
     * @param productSkuDto 参数对象
     * @return sku列表
     */
    @Operation(summary = "根据条件分页查询Sku接口")
    @GetMapping(value = "/{page}/{limit}")
    public Result<PageInfo<ProductSku>> querySkuByCriteriaByPage(
            @Parameter(name = "page", description = "当前页码", required = true) @PathVariable Integer page,
            @Parameter(name = "limit", description = "每页记录数", required = true) @PathVariable Integer limit,
            @Parameter(name = "productSkuDto", description = "搜索条件对象", required = false) ProductSkuDto productSkuDto) {
        PageInfo<ProductSku> productSkuList = productService.querySkuByCriteriaByPage(page, limit, productSkuDto);
        return Result.build(productSkuList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据skuId查询商品详细数据接口
     *
     * @param skuId skuId
     * @return 商品详细数据Vo对象
     */
    @Operation(summary = "根据skuId查询商品详细数据接口")
    @GetMapping("/item/{skuId}")
    public Result<ProductItemVo> item(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo, ResultCodeEnum.SUCCESS);
    }
}
