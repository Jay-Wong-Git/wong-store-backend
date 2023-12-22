package com.wong.store.manager.controller;

import com.wong.store.manager.service.SysUserService;
import com.wong.store.manager.service.ValidateCodeService;
import com.wong.store.model.dto.system.LoginDto;
import com.wong.store.model.entity.system.SysUser;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.system.LoginVo;
import com.wong.store.model.vo.system.ValidateCodeVo;
import com.wong.store.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jay Wong
 * @date 2023/12/22 14:31
 */
@Tag(name = "用户接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private ValidateCodeService validateCodeService;

    // 生成图片验证码接口
    @Operation(summary = "生成图片验证码接口")
    @GetMapping("/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    // 用户登录接口
    @Operation(summary = "用户登录接口")
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    // 获取用户信息接口
    @Operation(summary = "获取用户信息接口")
    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo() {
        // 1.从ThreadLocal对象中获取用户信息
        SysUser sysUser = AuthContextUtil.get();
        // 2.将用户信息返回
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    // 用户退出接口
    @Operation(summary = "用户退出接口")
    @GetMapping("/logout")
    public Result<Void> logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
