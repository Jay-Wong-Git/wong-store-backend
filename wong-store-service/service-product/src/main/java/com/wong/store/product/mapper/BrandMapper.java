package com.wong.store.product.mapper;

import com.wong.store.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 18:13
 */
@Mapper
public interface BrandMapper {
    // 获取所有品牌
    List<Brand> queryAll();
}
