package com.wong.store.common.log.annotation;

import com.wong.store.common.log.aspect.LogAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jay Wong
 * @date 2023/12/27 20:04
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = LogAspect.class)  // 通过Import注解导入日志切面类到Spring容器中
public @interface EnableLogAspect {
}
