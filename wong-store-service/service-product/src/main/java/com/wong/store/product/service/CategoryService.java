package com.wong.store.product.service;

import com.wong.store.model.entity.product.Category;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 11:04
 */
public interface CategoryService {
    // 获取一级分类列表
    List<Category> queryLevelOneCategory();

    // 获取分类树形数据
    List<Category> queryAllAsTree();
}
