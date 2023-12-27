package com.wong.store.manager;

import com.wong.store.common.log.annotation.EnableLogAspect;
import com.wong.store.manager.properties.MinioProperties;
import com.wong.store.manager.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
