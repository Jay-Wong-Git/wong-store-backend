package com.wong.store.manager.service.impl;

import com.wong.store.common.exception.BusinessException;
import com.wong.store.manager.mapper.SysMenuMapper;
import com.wong.store.manager.service.SysMenuService;
import com.wong.store.manager.utils.MenuHelper;
import com.wong.store.model.entity.system.SysMenu;
import com.wong.store.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/24 19:51
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

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
        sysMenuMapper.save(sysMenu);
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
}
