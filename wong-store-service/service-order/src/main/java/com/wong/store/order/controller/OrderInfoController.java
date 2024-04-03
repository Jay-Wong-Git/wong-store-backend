package com.wong.store.order.controller;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.dto.h5.OrderInfoDto;
import com.wong.store.model.entity.order.OrderInfo;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.h5.TradeVo;
import com.wong.store.order.service.OrderInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jay Wong
 * @date 2023/12/30 21:32
 */
@Tag(name = "订单管理接口")
@RestController
@RequestMapping(value = "/api/order/orderInfo")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 确认下单接口
     *
     * @return TradeVo对象
     */
    @Operation(summary = "确认下单接口")
    @GetMapping("/auth/trade")
    public Result<TradeVo> trade() {
        TradeVo tradeVo = orderInfoService.queryTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 提交订单接口
     *
     * @param orderInfoDto 参数对象
     * @return 订单Id
     */
    @Operation(summary = "提交订单")
    @PostMapping("auth/submitOrder")
    public Result<Long> submitOrder(@Parameter(name = "orderInfoDto", description = "请求参数实体类", required = true) @RequestBody OrderInfoDto orderInfoDto) {
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据id获取订单信息接口
     *
     * @param orderId 订单id
     * @return 订单信息对象
     */
    @Operation(summary = "根据id获取订单信息接口")
    @GetMapping("/auth/{orderId}")
    public Result<OrderInfo> queryById(@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.queryById(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 立即购买接口
     *
     * @param skuId skuId
     * @return TradeVo对象
     */
    @Operation(summary = "立即购买接口")
    @GetMapping("/auth/buy/{skuId}")
    public Result<TradeVo> buy(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据订单状态分页查询订单信息接口
     *
     * @param current     当前页
     * @param limit       每页显示条数
     * @param orderStatus 订单状态 0:待付款 1:待发货 2:待收货 3:待评价 -1:已取消
     * @return 订单列表
     */
    @Operation(summary = "根据订单状态分页查询订单信息接口")
    @GetMapping("/auth/{current}/{limit}")
    public Result<PageInfo<OrderInfo>> queryByOrderStatusByPage(
            @Parameter(name = "current", description = "当前页码", required = true)
            @PathVariable Integer current,

            @Parameter(name = "limit", description = "每页记录数", required = true)
            @PathVariable Integer limit,

            @Parameter(name = "orderStatus", description = "订单状态", required = false)
            @RequestParam(required = false, defaultValue = "") Integer orderStatus) {
        PageInfo<OrderInfo> pageInfo = orderInfoService.queryByOrderStatusByPage(current, limit, orderStatus);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 用于远程调用，根据订单编号查询订单信息接口
     *
     * @param orderNo 订单编号
     * @return 订单信息
     */
    @Operation(summary = "根据订单编号查询订单信息接口")
    @GetMapping("/auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo queryByOrderNo(@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable String orderNo) {
        return orderInfoService.queryByOrderNo(orderNo);
    }
}
