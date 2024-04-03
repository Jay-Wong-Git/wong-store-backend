package com.wong.store.order.service;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.dto.h5.OrderInfoDto;
import com.wong.store.model.entity.order.OrderInfo;
import com.wong.store.model.vo.h5.TradeVo;

/**
 * @author Jay Wong
 * @date 2023/12/30 21:33
 */
public interface OrderInfoService {
    // 确认下单
    TradeVo queryTrade();

    // 提交订单
    Long submitOrder(OrderInfoDto orderInfoDto);

    // 根据id获取订单信息
    OrderInfo queryById(Long id);

    // 立即购买
    TradeVo buy(Long skuId);

    // 根据订单状态分页查询订单信息
    PageInfo<OrderInfo> queryByOrderStatusByPage(Integer current, Integer limit, Integer orderStatus);

    // 根据订单编号查询订单信息
    OrderInfo queryByOrderNo(String orderNo);
}
