package com.wong.store.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.wong.store.pay.properties.AlipayProperties;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jay Wong
 * @date 2023/12/31 21:20
 */
@Configuration
public class AlipayConfiguration {
    @Resource
    private AlipayProperties alipayProperties;

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(
                alipayProperties.getAlipayUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getAppPrivateKey(),
                AlipayProperties.FORMAT,
                AlipayProperties.CHARSET,
                alipayProperties.getAlipayPublicKey(),
                AlipayProperties.SIGN_TYPE);
    }

}
