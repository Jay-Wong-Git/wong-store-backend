package com.wong.store.manager.controller;

import com.github.pagehelper.PageInfo;
import com.wong.store.manager.service.SysUserService;
import com.wong.store.model.dto.system.AssignRoleDto;
import com.wong.store.model.dto.system.SysUserDto;
import com.wong.store.model.entity.system.SysUser;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jay Wong
 * @date 2023/12/23 17:54
 */
@Tag(name = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 根据条件分页查询用户接口
     *
     * @param current    当前页
     * @param limit      每页显示条数
     * @param sysUserDto 条件参数对象
     * @return 用户信息列表
     */
    @Operation(summary = "根据条件分页查询用户接口")
    @PostMapping("/queryByCriteriaByPage/{current}/{limit}")
    public Result<PageInfo<SysUser>> queryByCriteriaByPage(
            @PathVariable("current") Integer current,
            @PathVariable("limit") Integer limit,
            @RequestBody SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo = sysUserService.queryByCriteriaByPage(sysUserDto, current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加用户接口
     *
     * @param sysUser 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "添加用户接口")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改用户接口
     *
     * @param sysUser 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "修改用户接口")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody SysUser sysUser) {
        sysUserService.update(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据Id删除用户接口
     *
     * @param userId 用户 id
     * @return 不返回数据
     */
    @Operation(summary = "根据Id删除用户接口")
    @DeleteMapping("/deleteById/{userId}")
    public Result<Void> deleteById(@PathVariable("userId") Long userId) {
        sysUserService.deleteById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 给用户分配角色接口
     *
     * @param assignRoleDto 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "给用户分配角色接口")
    @PostMapping("/doAssign")
    public Result<Void> doAssign(@RequestBody AssignRoleDto assignRoleDto) {
        sysUserService.doAssign(assignRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
