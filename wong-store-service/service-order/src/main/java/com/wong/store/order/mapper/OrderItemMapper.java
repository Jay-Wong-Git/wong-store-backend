package com.wong.store.order.mapper;

import com.wong.store.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/30 22:47
 */
@Mapper
public interface OrderItemMapper {
    // 添加订单项
    void save(OrderItem orderItem);

    // 根据订单id获取订单分项列表
    List<OrderItem> queryByOrderId(Long orderId);
}
