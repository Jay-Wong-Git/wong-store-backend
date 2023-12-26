package com.wong.store.manager.mapper;

import com.wong.store.model.entity.product.Category;
import com.wong.store.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/25 22:09
 */
@Mapper
public interface CategoryMapper {
    // 根据父分类Id获取子分类
    List<Category> queryByParentId(Long parentId);

    // 根据父分类Id获取子分类数量
    int countByParentId(Long parentId);

    // 查询所有分类
    List<Category> queryAll();

    // 批量添加分类
    void saveBatch(List<CategoryExcelVo> categoryList);
}
