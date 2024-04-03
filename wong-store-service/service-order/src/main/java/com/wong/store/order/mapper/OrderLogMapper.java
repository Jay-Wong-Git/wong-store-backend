package com.wong.store.order.mapper;

import com.wong.store.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jay Wong
 * @date 2023/12/30 22:47
 */
@Mapper
public interface OrderLogMapper {
    // 添加订单日志
    void save(OrderLog orderLog);
}
