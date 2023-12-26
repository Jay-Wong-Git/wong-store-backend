package com.wong.store.manager.service.impl;

import com.wong.store.common.exception.BusinessException;
import com.wong.store.manager.mapper.SysMenuMapper;
import com.wong.store.manager.mapper.SysRoleMenuMapper;
import com.wong.store.manager.service.SysMenuService;
import com.wong.store.manager.utils.MenuHelper;
import com.wong.store.model.entity.system.SysMenu;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.system.SysMenuVo;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/24 19:51
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 获取所有菜单
     *
     * @return 菜单列表(树形结构)
     */
    @Override
    public List<SysMenu> queryAll() {
        // 1.查询所有菜单数据
        List<SysMenu> sysMenuList = sysMenuMapper.queryAll();
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return null;
        }
        // 2.使用工具类中的方法，将菜单数据封装为前端要求的树形格式并返回
        return MenuHelper.buildTree(sysMenuList);
    }

    /**
     * 添加菜单
     *
     * @param sysMenu 参数对象
     */
    @Override
    public void save(SysMenu sysMenu) {
        // 1.添加菜单
        sysMenuMapper.save(sysMenu);
        // 2.更新sys_role_menu表中所有相关父菜单isHalf为半开状态
        updateSysRoleMenu(sysMenu);
    }

    private void updateSysRoleMenu(SysMenu sysMenu) {
        // 1.获取当前菜单的父菜单
        SysMenu parentMenu = sysMenuMapper.queryById(sysMenu.getParentId());
        if (parentMenu != null) {
            // 2.更新父菜单为半开状态
            sysRoleMenuMapper.updateIsHalf(parentMenu.getId());
            // 3.递归更新
            updateSysRoleMenu(parentMenu);
        }
    }

    /**
     * 修改菜单
     *
     * @param sysMenu 参数对象
     */
    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    /**
     * 根据Id删除菜单
     *
     * @param menuId 菜单Id
     */
    @Override
    public void deleteById(Long menuId) {
        // 1.查询当前菜单是否包含子菜单
        int count = sysMenuMapper.countChildren(menuId);
        // 2.若包含子菜单禁止删除，抛出异常，返回错误信息
        if (count > 0) {
            throw new BusinessException(ResultCodeEnum.NODE_ERROR);
        }
        // 3.若不包含子菜单，直接删除
        sysMenuMapper.deleteById(menuId);
    }

    /**
     * 根据用户Id获取对应的菜单列表(树形)
     *
     * @param userId 用户Id
     * @return 树形菜单列表
     */
    @Override
    public List<SysMenuVo> querySysMenuVoListByUserId(Long userId) {
        // 1.获取指定用户相关的所有菜单
        List<SysMenu> sysMenuList = sysMenuMapper.querySysMenuListByUserId(userId);
        // 2.整理成树形结构
        List<SysMenu> treeSysMenu = MenuHelper.buildTree(sysMenuList);
        // 3.把SysMenu对象转换成SysMenuVo对象
        ArrayList<SysMenuVo> treeSysMenuVo = new ArrayList<>();
        for (SysMenu sysMenu : treeSysMenu) {
            SysMenuVo sysMenuVo = MenuHelper.transferToVo(sysMenu);
            treeSysMenuVo.add(sysMenuVo);
        }
        return treeSysMenuVo;
    }
}
