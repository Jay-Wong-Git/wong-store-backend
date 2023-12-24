package com.wong.store.manager.mapper;

import com.wong.store.model.dto.system.SysRoleDto;
import com.wong.store.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/23 12:36
 */
@Mapper
public interface SysRoleMapper {
    // 根据条件查询角色列表
    List<SysRole> queryByCriteria(SysRoleDto sysRoleDto);

    // 添加角色
    void save(SysRole sysRole);

    // 修改角色
    void update(SysRole sysRole);

    // 根据角色Id删除角色
    void deleteById(Long roleId);

    // 查询所有角色
    List<SysRole> queryAll();
}
