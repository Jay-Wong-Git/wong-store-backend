package com.wong.store.manager.controller;

import com.wong.store.common.log.annotation.Log;
import com.wong.store.common.log.enums.OperatorType;
import com.wong.store.manager.service.CategoryService;
import com.wong.store.model.entity.product.Category;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/25 22:06
 */
@Tag(name = "分类管理接口")
@RestController
@RequestMapping("/admin/product/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 根据父分类Id获取子分类接口
     *
     * @param parentId 父分类Id
     * @return 子分类列表
     */
    @Log(
            title = "根据父分类Id获取子分类接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveResponseData = false
    )
    @Operation(summary = "根据父分类Id获取子分类接口")
    @GetMapping("/queryByParentId/{parentId}")
    public Result<List<Category>> queryByParentId(@PathVariable("parentId") Long parentId) {
        List<Category> categoryList = categoryService.queryByParentId(parentId);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 导出分类数据为Excel接口
     *
     * @param response 响应对象
     */
    @Log(
            title = "导出分类数据为Excel接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveRequestData = false,
            isSaveResponseData = false
    )
    @Operation(summary = "导出分类数据为Excel接口")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }

    /**
     * 从外部Excel导入分类数据接口
     *
     * @param multipartFile 文件对象
     * @return 不返回数据
     */
    @Log(
            title = "从外部Excel导入分类数据接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveRequestData = false,
            isSaveResponseData = false
    )
    @Operation(summary = "从外部Excel导入分类数据接口")
    @PostMapping("/importData")
    public Result<Void> importData(@RequestParam("file") MultipartFile multipartFile) {
        categoryService.importData(multipartFile);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
