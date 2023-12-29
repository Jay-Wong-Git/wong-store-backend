package com.wong.store.common.annotation;

import com.wong.store.common.config.UserWebMvcConfiguration;
import com.wong.store.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加该注解可以自动将UserInfo存储到ThreadLocal中
 *
 * @author Jay Wong
 * @date 2023/12/29 17:51
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({UserLoginAuthInterceptor.class, UserWebMvcConfiguration.class})
public @interface EnableUserLoginAuthInterceptor {
}
