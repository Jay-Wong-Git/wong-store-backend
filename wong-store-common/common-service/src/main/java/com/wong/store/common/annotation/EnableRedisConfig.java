package com.wong.store.common.annotation;

import com.wong.store.common.config.RedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jay Wong
 * @date 2023/12/29 20:32
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RedisConfig.class)
public @interface EnableRedisConfig {
}
