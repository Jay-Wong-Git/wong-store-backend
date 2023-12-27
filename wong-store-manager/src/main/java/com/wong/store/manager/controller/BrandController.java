package com.wong.store.manager.controller;

import com.github.pagehelper.PageInfo;
import com.wong.store.common.log.annotation.Log;
import com.wong.store.common.log.enums.OperatorType;
import com.wong.store.manager.service.BrandService;
import com.wong.store.model.entity.product.Brand;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.Name;
import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 13:26
 */
@Tag(name = "品牌管理接口")
@RestController
@RequestMapping("/admin/product/brand")
public class BrandController {
    @Resource
    private BrandService brandService;

    /**
     * 分页查询品牌接口
     *
     * @param current 当前页
     * @param limit   每页显示条数
     * @return 品牌列表
     */
    @Log(
            title = "分页查询品牌接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveResponseData = false
    )
    @Operation(summary = "分页查询品牌接口")
    @GetMapping("/queryByPage/{current}/{limit}")
    public Result<PageInfo<Brand>> queryByPage(
            @PathVariable("current") Integer current,
            @PathVariable("limit") Integer limit) {
        PageInfo<Brand> brandList = brandService.queryByPage(current, limit);
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加品牌接口
     *
     * @param brand 参数对象
     * @return 不返回数据
     */
    @Log(
            title = "添加品牌接口",
            operatorType = OperatorType.MANAGE,
            businessType = 1,
            isSaveRequestData = false
    )
    @Operation(summary = "添加品牌接口")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改品牌接口
     *
     * @param brand 参数对象
     * @return 不返回数据
     */
    @Log(
            title = "修改品牌接口",
            operatorType = OperatorType.MANAGE,
            businessType = 2,
            isSaveRequestData = false
    )
    @Operation(summary = "修改品牌接口")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody Brand brand) {
        brandService.update(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除品牌接口
     *
     * @param brandId 品牌Id
     * @return 不返回数据
     */
    @Log(
            title = "删除品牌接口",
            operatorType = OperatorType.MANAGE,
            businessType = 3
    )
    @Operation(summary = "删除品牌接口")
    @DeleteMapping("/deleteById/{brandId}")
    public Result<Void> deleteById(@PathVariable("brandId") Long brandId) {
        brandService.deleteById(brandId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 获取所有品牌接口
     *
     * @return 品牌列表
     */
    @Log(
            title = "获取所有品牌接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveResponseData = false
    )
    @Operation(summary = "获取所有品牌接口")
    @GetMapping("/queryAll")
    public Result<List<Brand>> queryAll() {
        List<Brand> brandList = brandService.queryAll();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
