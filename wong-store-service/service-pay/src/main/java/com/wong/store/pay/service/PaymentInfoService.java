package com.wong.store.pay.service;

import com.wong.store.model.entity.pay.PaymentInfo;

/**
 * @author Jay Wong
 * @date 2023/12/31 20:57
 */
public interface PaymentInfoService {
    // 添加支付信息
    PaymentInfo save(String orderNo);
}
