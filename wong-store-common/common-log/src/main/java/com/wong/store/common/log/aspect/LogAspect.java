package com.wong.store.common.log.aspect;

import com.wong.store.common.log.Service.AsyncOperLogService;
import com.wong.store.common.log.annotation.Log;
import com.wong.store.common.log.utils.LogUtil;
import com.wong.store.model.entity.system.SysOperLog;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jay Wong
 * @date 2023/12/27 19:57
 */
@Aspect
@Component
@Slf4j
public class LogAspect {  // 环绕通知切面类定义
    @Resource
    private AsyncOperLogService asyncOperLogService;

    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) {

        // 构建前置参数
        SysOperLog sysOperLog = new SysOperLog();

        LogUtil.beforeHandleLog(sysLog, joinPoint, sysOperLog);

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            // 执行业务方法
            LogUtil.afterHandleLog(sysLog, proceed, sysOperLog, 0, null);
            // 构建响应结果参数
        } catch (Throwable e) {                                 // 代码执行进入到catch中，
            // 业务方法执行产生异常
            e.printStackTrace();                                // 打印异常信息
            LogUtil.afterHandleLog(sysLog, proceed, sysOperLog, 1, e.getMessage());
            // 手动抛出异常防止事务失效
            throw new RuntimeException();
        } finally {
            // 保存日志数据
            asyncOperLogService.saveSysOperLog(sysOperLog);
        }
        // 返回执行结果
        return proceed;
    }
}
