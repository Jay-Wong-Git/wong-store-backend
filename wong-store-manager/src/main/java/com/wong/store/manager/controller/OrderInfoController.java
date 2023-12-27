package com.wong.store.manager.controller;

import com.wong.store.common.log.annotation.Log;
import com.wong.store.common.log.enums.OperatorType;
import com.wong.store.manager.service.OrderInfoService;
import com.wong.store.model.dto.order.OrderStatisticsDto;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.order.OrderStatisticsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jay Wong
 * @date 2023/12/27 14:09
 */
@Tag(name = "订单信息管理接口")
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 获取订单统计数据接口
     *
     * @param orderStatisticsDto 参数对象
     * @return 订单统计信息对象
     */
    @Log(
            title = "获取订单统计数据接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveRequestData = false,
            isSaveResponseData = false
    )
    @Operation(summary = "获取订单统计数据接口")
    @PostMapping("/queryOrderStatisticData")
    public Result<OrderStatisticsVo> queryOrderStatisticData(
            @RequestBody OrderStatisticsDto orderStatisticsDto) {
        OrderStatisticsVo orderStatisticsVo = orderInfoService.queryOrderStatisticData(orderStatisticsDto);
        return Result.build(orderStatisticsVo, ResultCodeEnum.SUCCESS);
    }
}
