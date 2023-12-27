package com.wong.store.manager.controller;

import com.github.pagehelper.PageInfo;
import com.wong.store.common.log.annotation.Log;
import com.wong.store.common.log.enums.OperatorType;
import com.wong.store.manager.service.ProductService;
import com.wong.store.model.dto.product.ProductDto;
import com.wong.store.model.entity.product.Product;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jay Wong
 * @date 2023/12/26 19:48
 */
@Tag(name = "商品管理接口")
@RestController
@RequestMapping("/admin/product/product")
public class ProductController {
    @Resource
    private ProductService productService;

    /**
     * 根据条件分页获取商品接口
     *
     * @param current    当前页
     * @param limit      每页显示条数
     * @param productDto 参数对象
     * @return 商品列表
     */
    @Log(
            title = "根据条件分页获取商品接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveRequestData = false,
            isSaveResponseData = false
    )
    @Operation(summary = "根据条件分页获取商品接口")
    @PostMapping("/queryByCriteriaByPage/{current}/{limit}")
    public Result<PageInfo<Product>> queryByCriteriaByPage(
            @PathVariable("current") Integer current,
            @PathVariable("limit") Integer limit,
            @RequestBody ProductDto productDto) {
        PageInfo<Product> productList = productService.queryByCriteriaByPage(current, limit, productDto);
        return Result.build(productList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加商品接口
     *
     * @param product 参数对象
     * @return 不返回数据
     */
    @Log(
            title = "添加商品接口",
            operatorType = OperatorType.MANAGE,
            businessType = 1,
            isSaveRequestData = false
    )
    @Operation(summary = "添加商品接口")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody Product product) {
        productService.save(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据Id获取商品接口
     *
     * @param id 商品Id
     * @return 商品对象
     */
    @Log(
            title = "根据Id获取商品接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveResponseData = false
    )
    @Operation(summary = "根据Id获取商品接口")
    @GetMapping("/queryById/{id}")
    public Result<Product> queryById(@PathVariable("id") Long id) {
        Product product = productService.queryById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改商品接口
     *
     * @param product 参数对象
     * @return 不返回数据
     */
    @Log(
            title = "修改商品接口",
            operatorType = OperatorType.MANAGE,
            businessType = 2,
            isSaveRequestData = false
    )
    @Operation(summary = "修改商品接口")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody Product product) {
        productService.update(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据Id删除商品接口
     *
     * @param id 商品Id
     * @return 不返回数据
     */
    @Log(
            title = "根据Id删除商品接口",
            operatorType = OperatorType.MANAGE,
            businessType = 3
    )
    @Operation(summary = "根据Id删除商品接口")
    @DeleteMapping("/deleteById/{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 更新指定商品审核状态接口
     *
     * @param id          商品Id
     * @param auditStatus 审核状态
     * @return 不返回数据
     */
    @Log(
            title = "更新指定商品审核状态接口",
            operatorType = OperatorType.MANAGE,
            businessType = 2
    )
    @Operation(summary = "更新指定商品审核状态接口")
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result<Void> updateAuditStatus(
            @PathVariable("id") Long id,
            @PathVariable("auditStatus") Integer auditStatus) {
        productService.updateAuditStatus(id, auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 商品上架和下架接口
     *
     * @param id     商品Id
     * @param status 商品上架或下架 1上架；-1下架
     * @return 不返回数据
     */
    @Log(
            title = "商品上架和下架接口",
            operatorType = OperatorType.MANAGE,
            businessType = 2
    )
    @Operation(summary = "商品上架和下架接口")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<Void> updateStatus(
            @PathVariable("id") Long id,
            @PathVariable("status") Integer status) {
        productService.updateStatus(id, status);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
