package com.wong.store.pay.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.wong.store.common.exception.BusinessException;
import com.wong.store.model.entity.pay.PaymentInfo;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.pay.properties.AlipayProperties;
import com.wong.store.pay.service.AlipayService;
import com.wong.store.pay.service.PaymentInfoService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author Jay Wong
 * @date 2023/12/31 21:24
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {
    @Resource
    private AlipayClient alipayClient;

    @Resource
    private PaymentInfoService paymentInfoService;

    @Resource
    private AlipayProperties alipayProperties;

    /**
     * 支付宝下单
     *
     * @param orderNo 订单编号
     * @return
     */
    @SneakyThrows  // lombok的注解，对外声明异常
    @Override
    public String submitAlipay(String orderNo) {

        // 保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.save(orderNo);

        // 创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        // 同步回调
        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());

        // 异步回调
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());

        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no", paymentInfo.getOrderNo());
        map.put("product_code", "QUICK_WAP_WAY");
        //map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount", new BigDecimal("0.01"));
        map.put("subject", paymentInfo.getContent());
        alipayRequest.setBizContent(JSONObject.toJSONString(map));

        // 发送请求
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
            if (response.isSuccess()) {
                log.info("调用成功");
                return response.getBody();
            } else {
                log.info("调用失败");
                throw new BusinessException(ResultCodeEnum.DATA_ERROR);
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
