package com.wong.store.product.controller;

import com.wong.store.model.entity.product.ProductSku;
import com.wong.store.model.entity.product.Category;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.h5.IndexVo;
import com.wong.store.product.service.CategoryService;
import com.wong.store.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 11:01
 */
@Tag(name = "首页接口")
@RestController
@RequestMapping("/api/product/index")
//@CrossOrigin 配置跨域
public class IndexController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private ProductService productService;

    @Operation(summary = "获取首页数据接口")
    @GetMapping
    public Result<IndexVo> queryIndexData() {
        // 获取一级分类列表
        List<Category> categoryList = categoryService.queryLevelOneCategory();
        // 获取热销商品列表
        List<ProductSku> productSkuList = productService.queryHotProductSku();
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }
}
