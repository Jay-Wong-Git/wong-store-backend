package com.wong.store.pay.service;

/**
 * @author Jay Wong
 * @date 2023/12/31 21:24
 */
public interface AlipayService {
    // 支付宝下单
    String submitAlipay(String orderNo);
}
