package com.wong.store.manager.controller;

import com.github.pagehelper.PageInfo;
import com.wong.store.common.log.annotation.Log;
import com.wong.store.common.log.enums.OperatorType;
import com.wong.store.manager.service.ProductSpecService;
import com.wong.store.model.entity.product.ProductSpec;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 16:20
 */
@Tag(name = "商品规格管理接口")
@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {
    @Resource
    private ProductSpecService productSpecService;

    /**
     * 分页查询商品规格接口
     *
     * @param current 当前页
     * @param limit 每页显示条数
     * @return 商品规格列表
     */
    @Log(
            title = "分页查询商品规格接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveResponseData = false
    )
    @Operation(summary = "分页查询商品规格接口")
    @GetMapping("/queryByPage/{current}/{limit}")
    public Result<PageInfo<ProductSpec>> queryByPage(
            @PathVariable("current") Integer current,
            @PathVariable("limit") Integer limit) {
        PageInfo<ProductSpec> productSpecList = productSpecService.queryByPage(current, limit);
        return Result.build(productSpecList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加商品规格接口
     *
     * @param productSpec 参数对象
     * @return 不返回数据
     */
    @Log(
            title = "添加商品规格接口",
            operatorType = OperatorType.MANAGE,
            businessType = 1,
            isSaveRequestData = false
    )
    @Operation(summary = "添加商品规格接口")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改商品规格接口
     *
     * @param productSpec 参数对象
     * @return 不返回数据
     */
    @Log(
            title = "修改商品规格接口",
            operatorType = OperatorType.MANAGE,
            businessType = 2,
            isSaveRequestData = false
    )
    @Operation(summary = "修改商品规格接口")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody ProductSpec productSpec) {
        productSpecService.update(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据Id删除商品规格接口
     *
     * @param id id
     * @return 不返回数据
     */
    @Log(
            title = "根据Id删除商品规格接口",
            operatorType = OperatorType.MANAGE,
            businessType = 3
    )
    @Operation(summary = "根据Id删除商品规格接口")
    @DeleteMapping("/deleteById/{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id) {
        productSpecService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 查询所有商品规格接口
     *
     * @return 商品规格列表
     */
    @Log(
            title = "查询所有商品规格接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveResponseData = false
    )
    @Operation(summary = "查询所有商品规格接口")
    @GetMapping("/queryAll")
    public Result<List<ProductSpec>> queryAll() {
        List<ProductSpec> productSpecList = productSpecService.queryAll();
        return Result.build(productSpecList, ResultCodeEnum.SUCCESS);
    }
}
