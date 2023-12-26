package com.wong.store.manager.controller;

import com.github.pagehelper.PageInfo;
import com.wong.store.manager.service.CategoryBrandService;
import com.wong.store.model.dto.product.CategoryBrandDto;
import com.wong.store.model.entity.product.CategoryBrand;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jay Wong
 * @date 2023/12/26 14:56
 */
@Tag(name = "分类品牌管理接口")
@RestController
@RequestMapping("/admin/product/categoryBrand")
public class CategoryBrandController {
    @Resource
    private CategoryBrandService categoryBrandService;

    /**
     * 根据条件分页查询分类品牌信息接口
     *
     * @param current          当前页
     * @param limit            每页显示条数
     * @param categoryBrandDto 参数对象
     * @return 分类品牌列表
     */
    @Operation(summary = "根据条件分页查询分类品牌信息接口")
    @PostMapping("/queryByCriteriaByPage/{current}/{limit}")
    public Result<PageInfo<CategoryBrand>> queryByCriteriaByPage(
            @PathVariable("current") Integer current,
            @PathVariable("limit") Integer limit,
            @RequestBody CategoryBrandDto categoryBrandDto) {
        PageInfo<CategoryBrand> categoryBrandList = categoryBrandService.queryByCriteriaByPage(categoryBrandDto, current, limit);
        return Result.build(categoryBrandList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加分类品牌接口
     *
     * @param categoryBrand 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "添加分类品牌接口")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 修改分类品牌接口
     *
     * @param categoryBrand 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "修改分类品牌接口")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.update(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据id删除分类品牌接口
     * @param id id
     * @return 不返回数据
     */
    @Operation(summary = "删除分类品牌接口")
    @DeleteMapping("/deleteById/{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id) {
        categoryBrandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
