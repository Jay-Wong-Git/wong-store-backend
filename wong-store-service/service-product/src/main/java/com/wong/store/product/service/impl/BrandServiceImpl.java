package com.wong.store.product.service.impl;

import com.wong.store.model.entity.product.Brand;
import com.wong.store.product.mapper.BrandMapper;
import com.wong.store.product.service.BrandService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 18:11
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    /**
     * 获取全部品牌
     *
     * @return 品牌列表
     */
    @Override
    @Cacheable(
            value = "brand",
            key = "'all'",
            unless = "#result.size()==0"
    )
    public List<Brand> queryAll() {
        return brandMapper.queryAll();
    }
}
