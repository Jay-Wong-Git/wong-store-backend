package com.wong.store.manager.service.impl;

import com.wong.store.manager.mapper.ProductUnitMapper;
import com.wong.store.manager.service.ProductUnitService;
import com.wong.store.model.entity.base.ProductUnit;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 20:33
 */
@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Resource
    private ProductUnitMapper productUnitMapper;
    /**
     * 获取商品单位
     *
     * @return 商品单位列表
     */
    @Override
    public List<ProductUnit> queryAll() {
        return productUnitMapper.queryAll();
    }
}
