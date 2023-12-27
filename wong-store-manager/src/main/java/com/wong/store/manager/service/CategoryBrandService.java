package com.wong.store.manager.service;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.dto.product.CategoryBrandDto;
import com.wong.store.model.entity.product.Brand;
import com.wong.store.model.entity.product.CategoryBrand;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 14:58
 */
public interface CategoryBrandService {
    // 根据条件分页查询分类品牌信息
    PageInfo<CategoryBrand> queryByCriteriaByPage(CategoryBrandDto categoryBrandDto, Integer current, Integer limit);

    // 添加分类品牌
    void save(CategoryBrand categoryBrand);

    // 修改分类品牌
    void update(CategoryBrand categoryBrand);

    // 根据id删除分类品牌
    void deleteById(Long id);

    // 根据分类Id获取品牌
    List<Brand> queryBrandByCategoryId(Long categoryId);
}
