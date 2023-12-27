package com.wong.store.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wong.store.manager.mapper.CategoryBrandMapper;
import com.wong.store.manager.service.CategoryBrandService;
import com.wong.store.model.dto.product.CategoryBrandDto;
import com.wong.store.model.entity.product.Brand;
import com.wong.store.model.entity.product.CategoryBrand;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 14:58
 */
@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    /**
     * 根据条件分页查询分类品牌信息
     *
     * @param categoryBrandDto 参数对象
     * @param current          当前页
     * @param limit            每页显示条数
     * @return 分类品牌列表
     */
    @Override
    public PageInfo<CategoryBrand> queryByCriteriaByPage(CategoryBrandDto categoryBrandDto, Integer current, Integer limit) {
        PageHelper.startPage(current, limit);
        List<CategoryBrand> categoryBrandList = categoryBrandMapper.queryByCriteria(categoryBrandDto);
        return new PageInfo<>(categoryBrandList);
    }

    /**
     * 添加分类品牌
     *
     * @param categoryBrand 参数对象
     */
    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    /**
     * 修改分类品牌
     *
     * @param categoryBrand 参数对象
     */
    @Override
    public void update(CategoryBrand categoryBrand) {
        categoryBrandMapper.update(categoryBrand);
    }

    /**
     * 根据id删除分类品牌
     *
     * @param id id
     */
    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id);
    }

    /**
     * 根据分类Id获取品牌
     *
     * @param categoryId 分类Id
     * @return 品牌列表
     */
    @Override
    public List<Brand> queryBrandByCategoryId(Long categoryId) {
        return categoryBrandMapper.queryBrandByCategoryId(categoryId);
    }
}
