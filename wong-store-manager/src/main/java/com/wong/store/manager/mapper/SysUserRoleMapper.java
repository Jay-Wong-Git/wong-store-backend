package com.wong.store.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/24 14:34
 */
@Mapper
public interface SysUserRoleMapper {
    // 根据用户Id删除所有相关数据
    void deleteByUserId(@Param("userId") Long userId);

    // 给用户分配指定角色
    void save(@Param("userId") Long userId, @Param("roleIdList") List<Long> roleIdList);

    // 根据用户Id查询所有相关角色Id
    List<Long> queryAllRoleIdsByUserId(@Param("userId") Long userId);
}
