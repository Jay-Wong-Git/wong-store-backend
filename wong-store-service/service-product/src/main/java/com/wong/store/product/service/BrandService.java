package com.wong.store.product.service;

import com.wong.store.model.entity.product.Brand;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 18:11
 */
public interface BrandService {
    // 获取全部品牌
    List<Brand> queryAll();
}
