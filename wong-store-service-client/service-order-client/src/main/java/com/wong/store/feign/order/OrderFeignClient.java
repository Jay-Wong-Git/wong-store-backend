package com.wong.store.feign.order;

import com.wong.store.model.entity.order.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Jay Wong
 * @date 2023/12/31 20:50
 */
@FeignClient("service-order")
public interface OrderFeignClient {
    // 根据订单编号获取订单信息
    @GetMapping("/api/order/orderInfo/auth/getOrderInfoByOrderNo/{orderNo}")
    OrderInfo queryByOrderNo(@PathVariable("orderNo") String orderNo);
}
