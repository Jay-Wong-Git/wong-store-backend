package com.wong.store.order.mapper;

import com.wong.store.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/30 22:45
 */
@Mapper
public interface OrderInfoMapper {
    // 添加订单详情
    void save(OrderInfo orderInfo);

    // 根据id获取订单信息
    OrderInfo queryById(Long id);

    // 根据订单状态查询订单信息
    List<OrderInfo> queryByOrderStatus(Long userId, Integer orderStatus);

    // 根据订单编号查询订单信息
    OrderInfo queryByOrderNo(String orderNo);
}
