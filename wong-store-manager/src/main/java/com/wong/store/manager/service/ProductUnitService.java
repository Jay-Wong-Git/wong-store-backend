package com.wong.store.manager.service;

import com.wong.store.model.entity.base.ProductUnit;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 20:33
 */
public interface ProductUnitService {
    // 获取商品单位
    List<ProductUnit> queryAll();
}
