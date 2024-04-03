package com.wong.store.pay.mapper;

import com.wong.store.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jay Wong
 * @date 2023/12/31 20:58
 */
@Mapper
public interface PaymentInfoMapper {
    // 根据订单编号获取支付信息
    PaymentInfo queryByOrderNo(String orderNo);

    // 添加支付信息
    void save(PaymentInfo paymentInfo);
}
