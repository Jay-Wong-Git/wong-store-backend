package com.wong.store.manager.mapper;

import com.wong.store.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jay Wong
 * @date 2023/12/22 14:40
 */
@Mapper
public interface SysUserMapper {
    SysUser selectByUsername(String username);
}
