package com.wong.store.manager.mapper;

import com.wong.store.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jay Wong
 * @date 2023/12/26 20:52
 */
@Mapper
public interface ProductDetailMapper {
    // 添加商品详情
    void save(ProductDetails productDetails);

    // 根据商品Id获取商品详情
    ProductDetails queryByProductId(Long productId);

    // 更新商品详情
    void update(ProductDetails productDetails);

    // 根据商品Id删除商品详情
    void deleteByProductId(Long productId);
}
