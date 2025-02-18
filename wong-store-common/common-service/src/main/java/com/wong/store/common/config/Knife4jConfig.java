package com.wong.store.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {
    @Bean
    public GroupedOpenApi adminApi() {      // 创建了一个api接口的分组
        return GroupedOpenApi.builder()
                .group("admin-api")         // 分组名称
                .pathsToMatch("/admin/**")  // 接口请求路径规则
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {      // 创建了一个api接口的分组
        return GroupedOpenApi.builder()
                .group("user-api")         // 分组名称
                .pathsToMatch("/api/user/**")  // 接口请求路径规则
                .build();
    }

    @Bean
    public GroupedOpenApi productApi() {      // 创建了一个api接口的分组
        return GroupedOpenApi.builder()
                .group("product-api")         // 分组名称
                .pathsToMatch("/api/product/**")  // 接口请求路径规则
                .build();
    }

    /***
     * @description 自定义接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Wong Store API Doc")
                        .version("1.0")
                        .description("Wong Store API Documentation")
                        .contact(new Contact().name("Jay Wong"))); // 设定作者
    }

}