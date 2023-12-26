package com.wong.store.manager.service;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.entity.product.ProductSpec;

/**
 * @author Jay Wong
 * @date 2023/12/26 16:23
 */
public interface ProductSpecService {
    // 分页查询商品规格
    PageInfo<ProductSpec> queryByPage(Integer current, Integer limit);

    // 添加商品规格
    void save(ProductSpec productSpec);

    // 修改商品规格
    void update(ProductSpec productSpec);

    // 根据Id删除商品规格
    void deleteById(Long id);
}
