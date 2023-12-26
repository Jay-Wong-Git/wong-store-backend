package com.wong.store.manager.mapper;

import com.wong.store.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/24 19:52
 */
@Mapper
public interface SysMenuMapper {
    // 获取所有菜单数据
    List<SysMenu> queryAll();

    // 添加菜单
    void save(SysMenu sysMenu);

    // 修改菜单
    void update(SysMenu sysMenu);

    // 获取子菜单数量
    int countChildren(Long menuId);

    // 根据Id删除菜单
    void deleteById(Long menuId);

    // 根据用户Id获取相关菜单
    List<SysMenu> querySysMenuListByUserId(Long userId);

    // 根据菜单Id获取菜单
    SysMenu queryById(Long menuId);
}
