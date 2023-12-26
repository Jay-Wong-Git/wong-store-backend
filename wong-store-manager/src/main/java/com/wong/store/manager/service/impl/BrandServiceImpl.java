package com.wong.store.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wong.store.manager.mapper.BrandMapper;
import com.wong.store.manager.service.BrandService;
import com.wong.store.model.entity.product.Brand;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 13:28
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    /**
     * 分页查询品牌
     *
     * @param current 当前页
     * @param limit   每页显示条数
     * @return 品牌列表
     */
    @Override
    public PageInfo<Brand> queryByPage(Integer current, Integer limit) {
        PageHelper.startPage(current, limit);
        List<Brand> brandList = brandMapper.queryAll();
        return new PageInfo<>(brandList);
    }

    /**
     * 添加品牌
     *
     * @param brand 参数对象
     */
    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }

    /**
     * 修改品牌
     *
     * @param brand 参数对象
     */
    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    /**
     * 删除品牌
     *
     * @param brandId 品牌Id
     */
    @Override
    public void deleteById(Long brandId) {
        brandMapper.deleteById(brandId);
    }

    /**
     * 获取所有品牌
     *
     * @return 品牌列表
     */
    @Override
    public List<Brand> queryAll() {
        return brandMapper.queryAll();
    }
}
