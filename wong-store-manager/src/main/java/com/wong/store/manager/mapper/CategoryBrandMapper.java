package com.wong.store.manager.mapper;

import com.wong.store.model.dto.product.CategoryBrandDto;
import com.wong.store.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 14:59
 */
@Mapper
public interface CategoryBrandMapper {
    // 条件查询分类品牌信息
    List<CategoryBrand> queryByCriteria(CategoryBrandDto categoryBrandDto);

    // 添加分类品牌
    void save(CategoryBrand categoryBrand);

    // 修改分类品牌
    void update(CategoryBrand categoryBrand);

    // 根据id删除分类品牌
    void deleteById(Long id);
}
