package com.wong.store.manager.mapper;

import com.wong.store.model.dto.product.ProductDto;
import com.wong.store.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 19:52
 */
@Mapper
public interface ProductMapper {
    // 根据条件获取商品
    List<Product> queryByCriteria(ProductDto productDto);

    // 添加商品
    void save(Product product);

    // 根据Id获取商品
    Product queryById(Long id);

    // 更新商品
    void update(Product product);

    // 根据Id删除商品
    void deleteById(Long id);
}
