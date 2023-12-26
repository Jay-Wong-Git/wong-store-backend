package com.wong.store.manager.mapper;

import com.wong.store.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 16:24
 */
@Mapper
public interface ProductSpecMapper {
    // 查询所有商品规格
    List<ProductSpec> queryAll();

    // 添加商品规格
    void save(ProductSpec productSpec);

    // 修改商品规格
    void update(ProductSpec productSpec);

    // 根据Id删除商品规格
    void deleteById(Long id);
}
