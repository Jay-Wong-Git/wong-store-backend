package com.wong.store.common.config;

import com.wong.store.common.interceptor.UserLoginAuthInterceptor;
import jakarta.annotation.Resource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jay Wong
 * @date 2023/12/28 23:25
 */
public class UserWebMvcConfiguration implements WebMvcConfigurer {
    @Resource
    private UserLoginAuthInterceptor userLoginAuthInterceptor ;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginAuthInterceptor)
                .addPathPatterns("/api/**");
    }
}
