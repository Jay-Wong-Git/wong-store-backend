package com.wong.store.manager.service;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.entity.product.Brand;

/**
 * @author Jay Wong
 * @date 2023/12/26 13:28
 */
public interface BrandService {
    // 分页查询品牌
    PageInfo<Brand> queryByPage(Integer current, Integer limit);

    // 添加品牌
    void save(Brand brand);

    // 修改品牌
    void update(Brand brand);

    // 删除品牌
    void deleteById(Long brandId);
}
