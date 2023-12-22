package com.wong.store.manager.service;

import com.wong.store.model.dto.system.LoginDto;
import com.wong.store.model.vo.system.LoginVo;

/**
 * @author Jay Wong
 * @date 2023/12/22 14:34
 */
public interface SysUserService {
    // 用户登录
    LoginVo login(LoginDto loginDto);

    void logout(String token);
}
