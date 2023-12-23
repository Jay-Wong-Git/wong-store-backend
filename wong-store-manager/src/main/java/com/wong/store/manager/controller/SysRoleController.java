package com.wong.store.manager.controller;

import com.github.pagehelper.PageInfo;
import com.wong.store.manager.service.SysRoleService;
import com.wong.store.model.dto.system.SysRoleDto;
import com.wong.store.model.entity.system.SysRole;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jay Wong
 * @date 2023/12/22 22:41
 */
@Tag(name = "角色接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 分页查询角色列表
     *
     * @param current    当前页
     * @param limit      每页显示条数
     * @param sysRoleDto 参数对象
     * @return 角色信息列表
     */

    @Operation(summary = "分页查询角色接口")
    @PostMapping("/querySysRoleByPage/{current}/{limit}")
    public Result<PageInfo<SysRole>> querySysRoleByPage(
            @PathVariable("current") Integer current,
            @PathVariable("limit") Integer limit,
            @RequestBody SysRoleDto sysRoleDto) {
        // 后端接口如果使用了@RequestBody注解，则前端使用 data 属性，传递 json 格式的数据给后端
        // 后端接口如果没有使用@RequestBody注解，则前端使用 params 属性，通过查询字符串传递数据给后端
        PageInfo<SysRole> pageInfo = sysRoleService.querySysRoleByPage(sysRoleDto, current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加角色接口
     *
     * @param sysRole 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "添加角色接口")
    @PostMapping("/saveSysRole")
    public Result<Void> saveSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改角色接口
     *
     * @param sysRole 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "修改角色接口")
    @PutMapping("/updateSysRole")
    public Result<Void> updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除角色接口
     *
     * @param roleId 角色 id
     * @return 不返回数据
     */
    @Operation(summary = "删除角色接口")
    @DeleteMapping("/deleteSysRoleById/{roleId}")
    public Result<Void> deleteSysRoleById(@PathVariable("roleId") Long roleId) {
        sysRoleService.deleteSysRoleById(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
