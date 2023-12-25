package com.wong.store.manager.service;

import com.wong.store.model.dto.system.AssignMenuDto;

import java.util.Map;

/**
 * @author Jay Wong
 * @date 2023/12/25 10:31
 */
public interface SysRoleMenuService {
    // 根据角色Id查询菜单信息
    Map<String, Object> queryMenuByRoleId(Long roleId);

    // 给角色分配菜单
    void doAssign(AssignMenuDto assignMenuDto);
}
