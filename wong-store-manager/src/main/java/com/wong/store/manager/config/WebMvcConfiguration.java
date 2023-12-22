package com.wong.store.manager.config;

import com.wong.store.manager.interceptor.LoginAuthInterceptor;
import com.wong.store.manager.properties.UserAuthProperties;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jay Wong
 * @date 2023/12/22 16:24
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Resource
    private LoginAuthInterceptor loginAuthInterceptor;
    @Resource
    private UserAuthProperties userAuthProperties;

    // 配置允许跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 添加路径规则
                .allowCredentials(true)           // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")       // 允许请求来源的域规则
                .allowedMethods("*")
                .allowedHeaders("*");             // 允许所有的请求头
    }

    // 注册请求拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                .excludePathPatterns(userAuthProperties.getNoAuthUrls())
                .addPathPatterns("/**");
    }
}
