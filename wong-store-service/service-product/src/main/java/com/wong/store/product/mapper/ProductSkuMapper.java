package com.wong.store.product.mapper;

import com.wong.store.model.dto.h5.ProductSkuDto;
import com.wong.store.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 11:19
 */
@Mapper
public interface ProductSkuMapper {
    // 获取热销Sku
    List<ProductSku> queryHotProductSku();

    // 根据条件查询Sku
    List<ProductSku> queryByCriteria(ProductSkuDto productSkuDto);

    // 根据Id获取Sku
    ProductSku queryById(Long id);

    // 根据productId获取Sku列表
    List<ProductSku> queryByProductId(Long productId);
}
