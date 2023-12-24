package com.wong.store.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wong.store.common.exception.BusinessException;
import com.wong.store.manager.mapper.SysUserMapper;
import com.wong.store.manager.mapper.SysUserRoleMapper;
import com.wong.store.manager.service.SysUserService;
import com.wong.store.model.dto.system.AssignRoleDto;
import com.wong.store.model.dto.system.LoginDto;
import com.wong.store.model.dto.system.SysUserDto;
import com.wong.store.model.entity.system.SysUser;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.system.LoginVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
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
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录
     *
     * @param loginDto 参数对象
     * @return 返回数据对象
     */
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
        SysUser sysUser = sysUserMapper.queryByUsername(username);
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

    /**
     * 用户退出
     *
     * @param token 用户token
     */
    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }

    /**
     * 分页查询用户
     *
     * @param sysUserDto 参数对象
     * @param current    当前页
     * @param limit      每页显示条数
     * @return 用户信息列表
     */
    @Override
    public PageInfo<SysUser> queryByCriteriaByPage(SysUserDto sysUserDto, Integer current, Integer limit) {
        PageHelper.startPage(current, limit);
        List<SysUser> userList = sysUserMapper.queryByCriteria(sysUserDto);
        return new PageInfo<>(userList);
    }

    /**
     * 添加用户
     *
     * @param sysUser 参数对象
     */
    @Override
    public void save(SysUser sysUser) {
        // 1.查询用户是否已存在
        SysUser dbSysUser = sysUserMapper.queryByUsername(sysUser.getUserName());
        // 2.用户名已存在，抛出自定义异常
        if (dbSysUser != null) {
            throw new BusinessException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        // 3.用户名不存在，对密码进行加密
        String digestPassword = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(digestPassword);
        // 4.更新用户状态为正常
        sysUser.setStatus(0);
        // 5.执行添加用户操作
        sysUserMapper.save(sysUser);
    }

    /**
     * 修改用户
     *
     * @param sysUser 参数对象
     */
    @Override
    public void update(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    /**
     * 删除用户
     *
     * @param userId 用户 id
     */
    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId);
    }

    /**
     * 给用户分配角色
     *
     * @param assignRoleDto 参数对象
     */
    @Override
    public void doAssign(AssignRoleDto assignRoleDto) {
        // 1.删除之前用户的所有角色数据
        Long userId = assignRoleDto.getUserId();
        sysUserRoleMapper.deleteByUserId(userId);
        // 2.给用户重新分配角色
        List<Long> roleIdList = assignRoleDto.getRoleIdList();
        roleIdList.forEach(roleId -> sysUserRoleMapper.doAssign(userId, roleId));
    }
}
