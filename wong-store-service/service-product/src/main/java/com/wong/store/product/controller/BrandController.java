package com.wong.store.product.controller;

import com.wong.store.model.entity.product.Brand;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 18:10
 */
@Tag(name = "品牌接口")
@RestController
@RequestMapping(value = "/api/product/brand")

public class BrandController {
    @Resource
    private BrandService brandService;

    /**
     * 获取全部品牌接口
     *
     * @return 品牌列表
     */
    @Operation(summary = "获取全部品牌接口")
    @GetMapping("/findAll")
    public Result<List<Brand>> queryAll() {
        List<Brand> brandList = brandService.queryAll();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }

}
