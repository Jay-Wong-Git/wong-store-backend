package com.wong.store.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.wong.store.common.exception.BusinessException;
import com.wong.store.manager.mapper.SysUserMapper;
import com.wong.store.manager.service.SysUserService;
import com.wong.store.model.dto.system.LoginDto;
import com.wong.store.model.entity.system.SysUser;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.system.LoginVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay Wong
 * @date 2023/12/22 14:38
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
        // 0-1.获取输入的验证码和响应的key
        String dtoCaptcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();
        // 0-2.通过key来获取redis中存储的验证码
        String redisCaptcha = redisTemplate.opsForValue().get("user:login:captcha:" + codeKey);
        // 0-3.比较输入的验证码和redis中存储的验证码
        // 0-4.如果两个验证码不一致，返回校验失败信息
        // 0-5.如果一致，删除redis中存储的验证码
        if (StrUtil.isEmpty(redisCaptcha) || !StrUtil.equalsIgnoreCase(redisCaptcha, dtoCaptcha)) {
            throw new BusinessException(ResultCodeEnum.VALIDATE_CODE_ERROR);
        } else {
            redisTemplate.delete("user:login:captcha:" + codeKey);
        }
        // 1.获取输入的用户名
        String username = loginDto.getUserName();
        // 2.根据用户名查询数据库sys_user表
        SysUser sysUser = sysUserMapper.selectByUsername(username);
        // 3.根据用户名查不到对应信息，用户不存在，返回错误信息
        if (sysUser == null) {
            throw new BusinessException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 4.根据用户名可以查到对应信息，用户存在
        // 5.获取输入的密码，比较输入的密码与数据库中的密码是否一致
        String databasePassword = sysUser.getPassword();
        String dtoPassword = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes()); // 对输入的密码加密
        // 6.如果密码一致，登录成功；密码不一致，登录失败
        if (!databasePassword.equals(dtoPassword)) {
            throw new BusinessException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 7.登录成功，生成用户唯一标识token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // 8.将用户信息存储到redis
        redisTemplate.opsForValue().set(
                "user:login:" + token,
                JSON.toJSONString(sysUser),
                30,
                TimeUnit.MINUTES);
        // 9.返回loginVo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }
}
