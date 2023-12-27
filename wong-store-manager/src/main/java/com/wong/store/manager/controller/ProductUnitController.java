package com.wong.store.manager.controller;

import com.wong.store.common.log.annotation.Log;
import com.wong.store.common.log.enums.OperatorType;
import com.wong.store.manager.service.ProductUnitService;
import com.wong.store.model.entity.base.ProductUnit;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 20:30
 */
@Tag(name = "商品单位管理接口")
@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {
    @Resource
    private ProductUnitService productUnitService;

    /**
     * 获取商品单位接口
     *
     * @return 商品单位列表
     */
    @Log(
            title = "获取商品单位接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveResponseData = false
    )
    @Operation(summary = "获取商品单位接口")
    @GetMapping("/queryAll")
    public Result<List<ProductUnit>> queryAll() {
        List<ProductUnit> productUnitList = productUnitService.queryAll();
        return Result.build(productUnitList, ResultCodeEnum.SUCCESS);
    }
}
