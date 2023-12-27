package com.wong.store.common.log.Service;

import com.wong.store.model.entity.system.SysOperLog;

/**
 * @author Jay Wong
 * @date 2023/12/27 20:37
 */
public interface AsyncOperLogService {            // 保存日志数据
    public abstract void saveSysOperLog(SysOperLog sysOperLog);
}
