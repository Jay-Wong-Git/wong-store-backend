package com.wong.store.manager.mapper;

import com.wong.store.model.dto.system.SysRoleDto;
import com.wong.store.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/23 12:36
 */
@Mapper
public interface SysRoleMapper {
    // 查询角色列表
    List<SysRole> querySysRole(SysRoleDto sysRoleDto);

    // 添加角色
    void saveSysRole(SysRole sysRole);

    // 修改角色
    void updateSysRole(SysRole sysRole);

    // 删除角色
    void deleteSysRoleById(Long roleId);
}
