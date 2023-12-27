package com.wong.store.manager.mapper;

import com.wong.store.model.dto.order.OrderStatisticsDto;
import com.wong.store.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/27 14:13
 */
@Mapper
public interface OrderStatisticMapper {
    // 查询订单统计数据
    List<OrderStatistics> queryOrderStatisticData(OrderStatisticsDto orderStatisticsDto);

    // 添加订单统计数据
    void save(OrderStatistics orderStatistics);
}
