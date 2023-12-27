package com.wong.store.manager.controller;

import com.github.pagehelper.PageInfo;
import com.wong.store.common.log.annotation.Log;
import com.wong.store.common.log.enums.OperatorType;
import com.wong.store.manager.service.SysRoleService;
import com.wong.store.model.dto.system.SysRoleDto;
import com.wong.store.model.entity.system.SysRole;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Jay Wong
 * @date 2023/12/22 22:41
 */
@Tag(name = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 根据条件分页查询角色接口
     *
     * @param current    当前页
     * @param limit      每页显示条数
     * @param sysRoleDto 参数对象
     * @return 角色信息列表
     */
    @Log(
            title = "根据条件分页查询角色接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveRequestData = false,
            isSaveResponseData = false
    )
    @Operation(summary = "根据条件分页查询角色接口")
    @PostMapping("/queryByCriteriaByPage/{current}/{limit}")
    public Result<PageInfo<SysRole>> queryByCriteriaByPage(
            @PathVariable("current") Integer current,
            @PathVariable("limit") Integer limit,
            @RequestBody SysRoleDto sysRoleDto) {
        // 后端接口如果使用了@RequestBody注解，则前端使用 data 属性，传递 json 格式的数据给后端
        // 后端接口如果没有使用@RequestBody注解，则前端使用 params 属性，通过查询字符串传递数据给后端
        PageInfo<SysRole> pageInfo = sysRoleService.queryByCriteriaByPage(sysRoleDto, current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加角色接口
     *
     * @param sysRole 参数对象
     * @return 不返回数据
     */
    @Log(
            title = "添加角色接口",
            operatorType = OperatorType.MANAGE,
            businessType = 1,
            isSaveRequestData = false
    )
    @Operation(summary = "添加角色接口")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改角色接口
     *
     * @param sysRole 参数对象
     * @return 不返回数据
     */
    @Log(
            title = "修改角色接口",
            operatorType = OperatorType.MANAGE,
            businessType = 2,
            isSaveRequestData = false
    )
    @Operation(summary = "修改角色接口")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据Id删除角色接口
     *
     * @param roleId 角色 id
     * @return 不返回数据
     */
    @Log(
            title = "根据Id删除角色接口",
            operatorType = OperatorType.MANAGE,
            businessType = 3
    )
    @Operation(summary = "根据Id删除角色接口")
    @DeleteMapping("/deleteById/{roleId}")
    public Result<Void> deleteById(@PathVariable("roleId") Long roleId) {
        sysRoleService.deleteById(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据用户Id查询角色信息接口
     *
     * @return 所有角色列表及用户相关角色Id列表
     */
    @Log(
            title = "根据用户Id查询角色信息接口",
            operatorType = OperatorType.MANAGE,
            businessType = 0,
            isSaveResponseData = false
    )
    @Operation(summary = "根据用户Id查询角色信息接口")
    @GetMapping("/queryRoleByUserId/{userId}")
    public Result<Map<String, Object>> queryRoleByUserId(@PathVariable("userId") Long userId) {
        Map<String, Object> resultMap = sysRoleService.queryRoleByUserId(userId);
        return Result.build(resultMap, ResultCodeEnum.SUCCESS);
    }
}
