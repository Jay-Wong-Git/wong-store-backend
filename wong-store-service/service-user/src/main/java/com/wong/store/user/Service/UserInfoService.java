package com.wong.store.user.Service;

import com.wong.store.model.dto.h5.UserLoginDto;
import com.wong.store.model.dto.h5.UserRegisterDto;
import com.wong.store.model.vo.h5.UserInfoVo;

/**
 * @author Jay Wong
 * @date 2023/12/28 22:21
 */
public interface UserInfoService {
    // 会员注册
    void register(UserRegisterDto userRegisterDto);

    // 会员登录
    String login(UserLoginDto userLoginDto);

    // 获取当前登录用户信息
    UserInfoVo queryCurrentUserInfo(String token);
}
