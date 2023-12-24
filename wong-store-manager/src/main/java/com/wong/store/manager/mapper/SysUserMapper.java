package com.wong.store.manager.mapper;

import com.wong.store.model.dto.system.SysUserDto;
import com.wong.store.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/22 14:40
 */
@Mapper
public interface SysUserMapper {
    // 根据用户名查询用户
    SysUser queryByUsername(String username);

    // 根据指定条件查询用户
    List<SysUser> queryByCriteria(SysUserDto sysUserDto);

    // 添加用户
    void save(SysUser sysUser);

    // 更新用户
    void update(SysUser sysUser);

    // 根据Id删除用户
    void deleteById(Long userId);
}
