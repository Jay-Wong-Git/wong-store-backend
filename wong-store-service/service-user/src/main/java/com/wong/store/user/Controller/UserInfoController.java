package com.wong.store.user.Controller;

import com.wong.store.model.dto.h5.UserLoginDto;
import com.wong.store.model.dto.h5.UserRegisterDto;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.h5.UserInfoVo;
import com.wong.store.user.Service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jay Wong
 * @date 2023/12/28 22:20
 */
@Tag(name = "会员用户接口")
@RestController
@RequestMapping("/api/user/userInfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 会员注册接口
     *
     * @param userRegisterDto 参数对象
     * @return 不返回数据
     */
    @Operation(summary = "会员注册接口")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody UserRegisterDto userRegisterDto) {
        userInfoService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 会员登录接口
     *
     * @param userLoginDto 参数对象
     * @return
     */
    @Operation(summary = "会员登录接口")
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserLoginDto userLoginDto) {
        String token = userInfoService.login(userLoginDto);
        return Result.build(token, ResultCodeEnum.SUCCESS);
    }

    /**
     * 获取当前登录用户信息接口
     *
     * @param request 请求对象
     * @return 用户信息Vo对象
     */
    @Operation(summary = "获取当前登录用户信息接口")
    @GetMapping("/auth/getCurrentUserInfo")
    public Result<UserInfoVo> getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.queryCurrentUserInfo(token);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }
}
