package com.wong.store.pay;

import com.wong.store.common.annotation.EnableRedisConfig;
import com.wong.store.common.annotation.EnableUserLoginAuthInterceptor;
import com.wong.store.pay.properties.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Jay Wong
 * @date 2023/12/31 12:51
 */
@SpringBootApplication
@EnableUserLoginAuthInterceptor
@EnableRedisConfig
@EnableFeignClients({"com.wong.store"})
@EnableConfigurationProperties({AlipayProperties.class})
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
