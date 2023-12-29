package com.wong.store.product.controller;

import com.wong.store.model.entity.product.Category;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 11:54
 */
@Tag(name = "分类接口")
@RestController
@RequestMapping(value = "/api/product/category")
//@CrossOrigin 配置跨域
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @Operation(summary = "获取分类树形数据接口")
    @GetMapping("/findCategoryTree")
    public Result<List<Category>> queryAllAsTree() {
        List<Category> list = categoryService.queryAllAsTree();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
