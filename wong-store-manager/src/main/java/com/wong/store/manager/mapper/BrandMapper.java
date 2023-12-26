package com.wong.store.manager.mapper;

import com.wong.store.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 13:28
 */
@Mapper
public interface BrandMapper {
    // 查询所有品牌
    List<Brand> queryAll();

    // 添加品牌
    void save(Brand brand);

    // 修改品牌
    void update(Brand brand);

    // 删除品牌
    void deleteById(Long brandId);
}
