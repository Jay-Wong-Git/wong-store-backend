package com.wong.store.manager.mapper;

import com.wong.store.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jay Wong
 * @date 2023/12/27 14:11
 */
@Mapper
public interface OrderInfoMapper {
    // 根据指定日期查询订单统计数据
    OrderStatistics queryOrderStatisticDataByCreateTime(String createTime);
}
