package com.wong.store.product.service;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.dto.h5.ProductSkuDto;
import com.wong.store.model.entity.product.ProductSku;
import com.wong.store.model.vo.h5.ProductItemVo;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 11:04
 */
public interface ProductService {
    // 获取热销Sku
    List<ProductSku> queryHotProductSku();

    // 根据条件分页查询sku
    PageInfo<ProductSku> querySkuByCriteriaByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    // 根据skuId查询商品详细数据
    ProductItemVo item(Long skuId);

    // 根据skuId获取Sku
    ProductSku getSkuBySkuId(Long skuId);
}
