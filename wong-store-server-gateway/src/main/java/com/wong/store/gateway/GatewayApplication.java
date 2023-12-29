package com.wong.store.gateway;

import com.wong.store.common.annotation.EnableRedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jay Wong
 * @date 2023/12/28 13:10
 */
@SpringBootApplication
@EnableRedisConfig
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
