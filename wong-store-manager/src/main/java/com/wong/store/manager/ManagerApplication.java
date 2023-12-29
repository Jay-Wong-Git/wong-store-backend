package com.wong.store.manager;

import com.wong.store.common.annotation.EnableRedisConfig;
import com.wong.store.manager.properties.MinioProperties;
import com.wong.store.manager.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Jay Wong
 * @date 2023/12/22 14:28
 */
//@EnableLogAspect
@SpringBootApplication
@ComponentScan(basePackages = {"com.wong.store"})
@EnableConfigurationProperties({
        UserAuthProperties.class,
        MinioProperties.class})
@EnableScheduling
@EnableRedisConfig
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
