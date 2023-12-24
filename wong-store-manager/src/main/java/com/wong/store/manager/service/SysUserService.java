package com.wong.store.manager.service;

import com.github.pagehelper.PageInfo;
import com.wong.store.model.dto.system.AssignRoleDto;
import com.wong.store.model.dto.system.LoginDto;
import com.wong.store.model.dto.system.SysUserDto;
import com.wong.store.model.entity.system.SysUser;
import com.wong.store.model.vo.system.LoginVo;

/**
 * @author Jay Wong
 * @date 2023/12/22 14:34
 */
public interface SysUserService {
    // 用户登录
    LoginVo login(LoginDto loginDto);

    // 用户退出
    void logout(String token);

    // 分页查询用户
    PageInfo<SysUser> queryByCriteriaByPage(SysUserDto sysUserDto, Integer current, Integer limit);

    // 添加用户
    void save(SysUser sysUser);

    // 修改用户
    void update(SysUser sysUser);

    // 根据Id删除用户
    void deleteById(Long userId);

    // 给用户分配角色
    void doAssign(AssignRoleDto assignRoleDto);
}
