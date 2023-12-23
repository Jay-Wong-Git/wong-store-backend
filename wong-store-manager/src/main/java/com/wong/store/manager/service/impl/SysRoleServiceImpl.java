package com.wong.store.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wong.store.manager.mapper.SysRoleMapper;
import com.wong.store.manager.service.SysRoleService;
import com.wong.store.model.dto.system.SysRoleDto;
import com.wong.store.model.entity.system.SysRole;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/23 12:35
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 分页查询角色列表
     *
     * @param sysRoleDto 参数对象
     * @param current    当前页
     * @param limit      每页显示条数
     * @return 角色信息列表
     */
    @Override
    public PageInfo<SysRole> querySysRoleByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        // 设置分页参数
        PageHelper.startPage(current, limit);
        // 根据条件查询
        List<SysRole> roleList = sysRoleMapper.querySysRole(sysRoleDto);
        // 封装PageInfo对象并返回
        return new PageInfo<>(roleList);
    }

    /**
     * 添加或修改角色
     *
     * @param sysRole 参数对象
     */
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    /**
     * 修改角色
     * @param sysRole 参数对象
     */
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    /**
     * 删除角色
     * @param roleId 角色 id
     */
    @Override
    public void deleteSysRoleById(Long roleId) {
        sysRoleMapper.deleteSysRoleById(roleId);
    }
}
