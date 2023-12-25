package com.wong.store.manager.service;

import com.wong.store.model.entity.system.SysMenu;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/24 19:51
 */
public interface SysMenuService {
    // 获取所有菜单列表
    List<SysMenu> queryAll();

    // 添加菜单
    void save(SysMenu sysMenu);

    // 修改菜单
    void update(SysMenu sysMenu);

    // 根据Id删除菜单
    void deleteById(Long menuId);
}
