package com.wong.store.product.mapper;

import com.wong.store.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 11:11
 */
@Mapper
public interface CategoryMapper {
    // 获取一级分类列表
    List<Category> queryLevelOneCategory();

    // 获取所有分类
    List<Category> queryAll();
}
