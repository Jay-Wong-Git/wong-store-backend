package com.wong.store.manager.utils;

import com.wong.store.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Jay Wong
 * @date 2023/12/24 20:36
 */
public class MenuHelper {
    // 将菜单列表调整为树形结构
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> treeList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getParentId() == 0) {
                treeList.add(findChildren(sysMenu, sysMenuList));
            }
        }
        return treeList;
    }

    // 递归获取菜单的子菜单
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        sysMenu.setChildren(new ArrayList<>());
        for (SysMenu item : sysMenuList) {
            if (Objects.equals(sysMenu.getId(), item.getParentId())) {
                sysMenu.getChildren().add(findChildren(item, sysMenuList));
            }
        }
        return sysMenu;
    }
}
