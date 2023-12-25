package com.wong.store.manager.controller;

import com.wong.store.manager.service.SysMenuService;
import com.wong.store.model.entity.system.SysMenu;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/24 19:46
 */
@Tag(name = "菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 获取所有菜单接口
     *
     * @return 菜单列表(树形结构)
     */
    @Operation(summary = "获取所有菜单接口")
    @GetMapping("/queryAll")
    public Result<List<SysMenu>> queryAll() {
        List<SysMenu> sysMenuList = sysMenuService.queryAll();
        return Result.build(sysMenuList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加菜单接口
     *
     * @param sysMenu 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "添加菜单接口")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改菜单接口
     *
     * @param sysMenu 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "修改菜单接口")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody SysMenu sysMenu) {
        sysMenuService.update(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据Id删除菜单接口
     *
     * @param menuId 菜单Id
     * @return 不返回数据
     */
    @Operation(summary = "根据Id删除菜单接口")
    @DeleteMapping("/deleteById/{menuId}")
    public Result<Void> deleteById(@PathVariable("menuId") Long menuId) {
        sysMenuService.deleteById(menuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
