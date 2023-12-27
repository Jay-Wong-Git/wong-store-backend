package com.wong.store.manager.task;

import cn.hutool.core.date.DateUtil;
import com.wong.store.manager.mapper.OrderInfoMapper;
import com.wong.store.manager.mapper.OrderStatisticMapper;
import com.wong.store.model.entity.order.OrderStatistics;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Jay Wong
 * @date 2023/12/27 14:58
 */
@Component
@Slf4j
public class OrderStatisticTask {
    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderStatisticMapper orderStatisticMapper;

    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics() {
        log.info(new Date().toString());
        // 1.获取前一天日期
        String createTime = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
        // 2.统计订单数据
        OrderStatistics orderStatistics = orderInfoMapper.queryOrderStatisticDataByCreateTime(createTime);
        // 3.将统计结果保存到数据库
        if (orderStatistics != null) {
            orderStatisticMapper.save(orderStatistics);
        }
    }
}
