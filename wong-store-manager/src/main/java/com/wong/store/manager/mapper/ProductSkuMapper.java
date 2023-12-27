package com.wong.store.manager.mapper;

import com.wong.store.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 20:52
 */
@Mapper
public interface ProductSkuMapper {
    // 添加商品SKU
    void save(ProductSku productSku);

    // 根据商品Id获取商品SKU列表
    List<ProductSku> queryByProductId(Long productId);

    // 更新商品SKU
    void update(ProductSku productSku);

    // 根据商品Id删除商品SKU
    void deleteByProductId(Long productId);
}
