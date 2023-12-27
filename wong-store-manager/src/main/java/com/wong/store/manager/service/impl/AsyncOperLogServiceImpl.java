package com.wong.store.manager.service.impl;

import com.wong.store.common.log.Service.AsyncOperLogService;
import com.wong.store.manager.mapper.SysOperLogMapper;
import com.wong.store.model.entity.system.SysOperLog;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Jay Wong
 * @date 2023/12/27 20:38
 */
@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Resource
    private SysOperLogMapper sysOperLogMapper;

    @Async      // 异步执行保存日志操作
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.save(sysOperLog);
    }

}
