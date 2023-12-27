package com.wong.store.manager.mapper;

import com.wong.store.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jay Wong
 * @date 2023/12/27 20:40
 */
@Mapper
public interface SysOperLogMapper {
    public abstract void save(SysOperLog sysOperLog);
}
