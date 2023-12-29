package com.wong.store.product.mapper;

import com.wong.store.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jay Wong
 * @date 2023/12/28 21:03
 */
@Mapper
public interface ProductDetailsMapper {
    // 根据productId获取商品详情
    ProductDetails queryByProductId(Long productId);
}
