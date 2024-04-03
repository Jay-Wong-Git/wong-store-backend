package com.wong.store.order;

import com.wong.store.common.annotation.EnableRedisConfig;
import com.wong.store.common.annotation.EnableUserLoginAuthInterceptor;
import com.wong.store.common.annotation.EnableUserTokenFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Jay Wong
 * @date 2023/12/30 21:26
 */
@SpringBootApplication
@EnableFeignClients({"com.wong.store"})
@EnableUserTokenFeignInterceptor
@EnableUserLoginAuthInterceptor
@EnableRedisConfig
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
