package com.wong.store.user;

import com.wong.store.common.annotation.EnableRedisConfig;
import com.wong.store.common.annotation.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Jay Wong
 * @date 2023/12/28 21:48
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wong.store"})
@EnableUserLoginAuthInterceptor
@EnableRedisConfig
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
