package com.wong.store.product.mapper;

import com.wong.store.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jay Wong
 * @date 2023/12/28 21:03
 */
@Mapper
public interface ProductMapper {
    // 根据Id查询商品
    Product queryById(Long id);
}
