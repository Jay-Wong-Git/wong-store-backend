package com.wong.store.manager.service;

import com.wong.store.model.dto.order.OrderStatisticsDto;
import com.wong.store.model.vo.order.OrderStatisticsVo;

/**
 * @author Jay Wong
 * @date 2023/12/27 14:12
 */
public interface OrderInfoService {
    // 获取订单统计信息
    OrderStatisticsVo queryOrderStatisticData(OrderStatisticsDto orderStatisticsDto);
}
