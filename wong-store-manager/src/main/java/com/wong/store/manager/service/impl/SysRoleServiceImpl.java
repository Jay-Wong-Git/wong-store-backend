package com.wong.store.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wong.store.manager.mapper.SysRoleMapper;
import com.wong.store.manager.mapper.SysUserRoleMapper;
import com.wong.store.manager.service.SysRoleService;
import com.wong.store.model.dto.system.SysRoleDto;
import com.wong.store.model.entity.system.SysRole;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jay Wong
 * @date 2023/12/23 12:35
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 分页查询角色列表
     *
     * @param sysRoleDto 参数对象
     * @param current    当前页
     * @param limit      每页显示条数
     * @return 角色信息列表
     */
    @Override
    public PageInfo<SysRole> queryByCriteriaByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        // 设置分页参数
        PageHelper.startPage(current, limit);
        // 根据条件查询
        List<SysRole> roleList = sysRoleMapper.queryByCriteria(sysRoleDto);
        // 封装PageInfo对象并返回
        return new PageInfo<>(roleList);
    }

    /**
     * 添加或修改角色
     *
     * @param sysRole 参数对象
     */
    @Override
    public void save(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    /**
     * 修改角色
     *
     * @param sysRole 参数对象
     */
    @Override
    public void update(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    /**
     * 删除角色
     *
     * @param roleId 角色 id
     */
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    /**
     * 根据用户Id查询角色信息接口
     *
     * @return 所有角色列表及用户相关角色Id列表
     */
    @Override
    public Map<String, Object> queryRoleByUserId(Long userId) {
        // 1.获取所有角色
        List<SysRole> sysRoleList = sysRoleMapper.queryAll();
        // 2.获取用户相关的所有角色Id
        List<Long> roleIdList = sysUserRoleMapper.queryAllRoleIdsByUserId(userId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("sysRoleList", sysRoleList);
        resultMap.put("roleIdList", roleIdList);
        return resultMap;
    }
}
