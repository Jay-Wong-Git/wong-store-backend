package com.wong.store.product.service.impl;

import com.wong.store.model.entity.product.Category;
import com.wong.store.product.mapper.CategoryMapper;
import com.wong.store.product.service.CategoryService;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jay Wong
 * @date 2023/12/28 11:10
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 获取一级分类列表
     *
     * @return 一级分类列表
     */
    @Cacheable(
            value = "category",
            key = "'levelOne'",
            unless = "#result.size()==0"
    )
    @Override
    public List<Category> queryLevelOneCategory() {
        return categoryMapper.queryLevelOneCategory();
    }

    /**
     * 获取分类树形数据
     *
     * @return 树形分类列表
     */
    @Cacheable(
            value = "category",
            key = "'all'",
            unless = "#result.size()==0"
    )
    @Override
    public List<Category> queryAllAsTree() {
        // 1.获取所有分类
        List<Category> categoryList = categoryMapper.queryAll();
        // 2.获取所有一级分类
        List<Category> levelOneCategoryList = categoryList
                .stream()
                .filter(category -> category.getParentId() == 0)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(levelOneCategoryList)) {
            levelOneCategoryList.forEach(levelOneCategory -> {
                // 3.获取指定一级分类下的所有二级分类
                List<Category> levelTwoCategoryList = categoryList
                        .stream()
                        .filter(category -> Objects.equals(category.getParentId(), levelOneCategory.getId()))
                        .collect(Collectors.toList());
                // 4.将二级分类列表设置给对应一级分类的children属性
                levelOneCategory.setChildren(levelTwoCategoryList);
                if (!CollectionUtils.isEmpty(levelTwoCategoryList)) {
                    levelTwoCategoryList.forEach(levelTwoCategory -> {
                        // 5.获取指定二级分类下的所有三级分类
                        List<Category> levelThreeCategoryList = categoryList
                                .stream()
                                .filter(category -> Objects.equals(category.getParentId(), levelTwoCategory.getId()))
                                .collect(Collectors.toList());
                        // 6.将三级分类列表设置给对应二级分类的children属性
                        levelTwoCategory.setChildren(levelThreeCategoryList);
                    });
                }
            });
        }
        return levelOneCategoryList;
    }
}
