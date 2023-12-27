package com.wong.store.manager.mapper;

import com.wong.store.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 20:32
 */
@Mapper
public interface ProductUnitMapper {
    // 获取商品单位
    List<ProductUnit> queryAll();
}
