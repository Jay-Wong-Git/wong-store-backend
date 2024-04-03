package com.wong.store.pay.service.impl;

import com.wong.store.feign.order.OrderFeignClient;
import com.wong.store.model.entity.order.OrderInfo;
import com.wong.store.model.entity.order.OrderItem;
import com.wong.store.model.entity.pay.PaymentInfo;
import com.wong.store.pay.mapper.PaymentInfoMapper;
import com.wong.store.pay.service.PaymentInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Jay Wong
 * @date 2023/12/31 20:57
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Resource
    private PaymentInfoMapper paymentInfoMapper;
    @Resource
    private OrderFeignClient orderFeignClient;

    /**
     * 添加支付信息
     *
     * @param orderNo 订单编号
     * @return 支付信息
     */
    @Override
    public PaymentInfo save(String orderNo) {
        // 查询支付信息数据，如果已经已经存在了就不用进行保存(一个订单支付失败以后可以继续支付)
        PaymentInfo paymentInfo = paymentInfoMapper.queryByOrderNo(orderNo);
        if (null == paymentInfo) {
            OrderInfo orderInfo = orderFeignClient.queryByOrderNo(orderNo);
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            StringBuilder content = new StringBuilder();
            for (OrderItem item : orderInfo.getOrderItemList()) {
                content.append(item.getSkuName()).append(" ");
            }
            paymentInfo.setContent(content.toString());
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            paymentInfoMapper.save(paymentInfo);
        }
        return paymentInfo;
    }
}
