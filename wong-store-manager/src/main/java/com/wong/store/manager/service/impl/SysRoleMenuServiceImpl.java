package com.wong.store.manager.service.impl;

import com.wong.store.manager.mapper.SysMenuMapper;
import com.wong.store.manager.mapper.SysRoleMapper;
import com.wong.store.manager.mapper.SysRoleMenuMapper;
import com.wong.store.manager.service.SysMenuService;
import com.wong.store.manager.service.SysRoleMenuService;
import com.wong.store.model.dto.system.AssignMenuDto;
import com.wong.store.model.entity.system.SysMenu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jay Wong
 * @date 2023/12/25 10:31
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 根据角色Id查询菜单信息
     *
     * @param roleId 角色Id
     * @return 所有菜单列表及指定角色对应的菜单Id列表
     */
    @Override
    public Map<String, Object> queryMenuByRoleId(Long roleId) {
        // 1.获取所有菜单列表(树形结构)
        List<SysMenu> sysMenuList = sysMenuService.queryAll();
        // 2.根据角色Id获取对应的菜单Id列表
        List<Long> menuIdList = sysRoleMenuMapper.queryAllMenuIdsByRoleId(roleId);
        // 3.整理结果并返回
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("sysMenuList", sysMenuList);
        resultMap.put("menuIdList", menuIdList);
        return resultMap;
    }

    /**
     * 给角色分配菜单
     *
     * @param assignMenuDto 参数对象
     */
    @Override
    public void doAssign(AssignMenuDto assignMenuDto) {
        // 1.删除角色之前对应的菜单
        Long roleId = assignMenuDto.getRoleId();
        sysRoleMenuMapper.deleteByRoleId(roleId);
        // 2.给角色重新分配菜单
        List<Map<String, Number>> menuInfoList = assignMenuDto.getMenuIdList();
        if (menuInfoList != null && menuInfoList.size() > 0) {

//            menuInfoList.forEach(menuInfo -> sysRoleMenuMapper.save(roleId, menuInfo));
            sysRoleMenuMapper.save(roleId, menuInfoList);
        }
    }
}
