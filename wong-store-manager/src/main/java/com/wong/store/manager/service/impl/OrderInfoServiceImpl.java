package com.wong.store.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.wong.store.manager.mapper.OrderStatisticMapper;
import com.wong.store.manager.service.OrderInfoService;
import com.wong.store.model.dto.order.OrderStatisticsDto;
import com.wong.store.model.entity.order.OrderStatistics;
import com.wong.store.model.vo.order.OrderStatisticsVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jay Wong
 * @date 2023/12/27 14:12
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Resource
    private OrderStatisticMapper orderStatisticMapper;

    /**
     * 获取订单统计信息
     *
     * @param orderStatisticsDto 参数对象
     * @return 订单统计信息对象
     */
    @Override
    public OrderStatisticsVo queryOrderStatisticData(OrderStatisticsDto orderStatisticsDto) {
        // 查询统计结果
        List<OrderStatistics> orderStatisticsList = orderStatisticMapper.queryOrderStatisticData(orderStatisticsDto);
        // 生成日期列表
        List<String> dateList = orderStatisticsList.stream()
                .map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))
                .toList();
        // 生成金额列表
        List<BigDecimal> amountList = orderStatisticsList.stream()
                .map(OrderStatistics::getTotalAmount)
                .toList();
        // 创建OrderStatisticsVo对象用于封装数据
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);
        return orderStatisticsVo;
    }
}
