package com.wong.store.manager.service;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.dto.product.ProductDto;
import com.wong.store.model.entity.product.Product;

/**
 * @author Jay Wong
 * @date 2023/12/26 19:53
 */
public interface ProductService {
    // 根据条件分页获取商品
    PageInfo<Product> queryByCriteriaByPage(Integer current, Integer limit, ProductDto productDto);

    // 添加商品
    void save(Product product);

    // 根据Id获取商品
    Product queryById(Long id);

    // 修改商品
    void update(Product product);

    // 根据Id删除商品
    void deleteById(Long id);

    // 更新商品审核状态
    void updateAuditStatus(Long id, Integer auditStatus);

    // 商品上架或下架
    void updateStatus(Long id, Integer status);
}
