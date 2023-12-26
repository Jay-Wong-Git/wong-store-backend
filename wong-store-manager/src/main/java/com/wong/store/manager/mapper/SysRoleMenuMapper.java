package com.wong.store.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Jay Wong
 * @date 2023/12/25 10:32
 */
@Mapper
public interface SysRoleMenuMapper {
    // 获取指定角色Id对应的菜单Id列表
    List<Long> queryAllMenuIdsByRoleId(Long roleId);

    // 根据角色Id删除相关数据
    void deleteByRoleId(Long roleId);

    // 给指定角色分配指定菜单
    void save(Long roleId, List<Map<String, Number>> menuInfoList);

    // 根据菜单Id修改is_half状态
    void updateIsHalf(Long menuId);
}
