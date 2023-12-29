package com.wong.store.cart;

import com.wong.store.common.annotation.EnableRedisConfig;
import com.wong.store.common.annotation.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Jay Wong
 * @date 2023/12/29 19:11
 */
// 排除数据库的自动化配置，Cart微服务不需要访问数据库
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableRedisConfig
@EnableUserLoginAuthInterceptor
@EnableFeignClients({"com.wong.store"})
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

}
