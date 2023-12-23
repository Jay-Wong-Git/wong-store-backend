package com.wong.store.manager.service;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.dto.system.SysRoleDto;
import com.wong.store.model.entity.system.SysRole;

/**
 * @author Jay Wong
 * @date 2023/12/23 12:35
 */
public interface SysRoleService {
    // 分页查询角色列表
    PageInfo<SysRole> querySysRoleByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

    // 添加角色
    void saveSysRole(SysRole sysRole);

    // 修改角色
    void updateSysRole(SysRole sysRole);

    // 删除角色
    void deleteSysRoleById(Long roleId);
}
