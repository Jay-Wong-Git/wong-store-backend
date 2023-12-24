package com.wong.store.manager.service;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.dto.system.SysRoleDto;
import com.wong.store.model.entity.system.SysRole;

import java.util.List;
import java.util.Map;

/**
 * @author Jay Wong
 * @date 2023/12/23 12:35
 */
public interface SysRoleService {
    // 根据条件分页查询角色列表
    PageInfo<SysRole> queryByCriteriaByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

    // 添加角色
    void save(SysRole sysRole);

    // 修改角色
    void update(SysRole sysRole);

    // 删除角色
    void deleteById(Long roleId);

    // 查询所有角色
    Map<String, Object> queryAll(Long userId);
}
