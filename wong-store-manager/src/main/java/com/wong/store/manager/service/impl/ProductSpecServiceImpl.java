package com.wong.store.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wong.store.manager.mapper.ProductSpecMapper;
import com.wong.store.manager.service.ProductSpecService;
import com.wong.store.model.entity.product.ProductSpec;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 16:23
 */
@Service
public class ProductSpecServiceImpl implements ProductSpecService {
    @Resource
    private ProductSpecMapper productSpecMapper;

    /**
     * 分页查询商品规格
     *
     * @param current 当前页
     * @param limit   每页显示条数
     * @return 商品规格列表
     */
    @Override
    public PageInfo<ProductSpec> queryByPage(Integer current, Integer limit) {
        PageHelper.startPage(current, limit);
        List<ProductSpec> productSpecList = productSpecMapper.queryAll();
        return new PageInfo<>(productSpecList);
    }

    /**
     * 添加商品规格
     *
     * @param productSpec 参数对象
     */
    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec);
    }

    /**
     * 修改商品规格
     *
     * @param productSpec 参数对象
     */
    @Override
    public void update(ProductSpec productSpec) {
        productSpecMapper.update(productSpec);
    }

    /**
     * 根据Id删除商品规格
     *
     * @param id id
     */
    @Override
    public void deleteById(Long id) {
        productSpecMapper.deleteById(id);
    }
}
