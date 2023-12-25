package com.wong.store.manager.controller;

import com.wong.store.manager.service.SysRoleMenuService;
import com.wong.store.model.dto.system.AssignMenuDto;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Jay Wong
 * @date 2023/12/25 10:25
 */
@Tag(name = "角色对应菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysRoleMenu")
public class SysRoleMenuController {
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 根据角色Id查询菜单信息接口
     *
     * @param roleId 角色Id
     * @return 所有菜单列表及指定角色对应的菜单Id列表
     */
    @Operation(summary = "根据角色Id查询菜单信息接口")
    @GetMapping("/queryMenuByRoleId/{roleId}")
    public Result<Map<String, Object>> queryMenuByRoleId(@PathVariable("roleId") Long roleId) {
        Map<String, Object> resultMap = sysRoleMenuService.queryMenuByRoleId(roleId);
        return Result.build(resultMap, ResultCodeEnum.SUCCESS);
    }

    /**
     * 给角色分配菜单接口
     *
     * @param assignMenuDto 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "给角色分配菜单接口")
    @PostMapping("/doAssign")
    public Result<Void> doAssign(@RequestBody AssignMenuDto assignMenuDto) {
        sysRoleMenuService.doAssign(assignMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
