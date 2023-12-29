package com.wong.store.user.Service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wong.store.common.exception.BusinessException;
import com.wong.store.model.dto.h5.UserLoginDto;
import com.wong.store.model.dto.h5.UserRegisterDto;
import com.wong.store.model.entity.user.UserInfo;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.h5.UserInfoVo;
import com.wong.store.user.Service.UserInfoService;
import com.wong.store.user.mapper.UserInfoMapper;
import com.wong.store.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay Wong
 * @date 2023/12/28 22:21
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 会员注册
     *
     * @param userRegisterDto 参数对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserRegisterDto userRegisterDto) {

        // 获取数据
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();

        // 校验参数
        if (StringUtils.isEmpty(username) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(code)) {
            throw new BusinessException(ResultCodeEnum.DATA_ERROR);
        }

        // 校验校验验证码
        String codeValueRedis = redisTemplate.opsForValue().get("phone:code:" + username);
        if (!code.equals(codeValueRedis)) {
            throw new BusinessException(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }

        // 查询用户名是否已存在
        UserInfo userInfo = userInfoMapper.queryByUsername(username);
        if (null != userInfo) {
            throw new BusinessException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //保存用户信息
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);

        // 删除Redis中的数据
        redisTemplate.delete("phone:code:" + username);
    }

    /**
     * 会员登录
     *
     * @param userLoginDto 参数对象
     * @return token
     */
    @Override
    public String login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        // 校验参数
        if (StringUtils.isEmpty(username) ||
                StringUtils.isEmpty(password)) {
            throw new BusinessException(ResultCodeEnum.DATA_ERROR);
        }

        UserInfo userInfo = userInfoMapper.queryByUsername(username);
        if (null == userInfo) {
            throw new BusinessException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 校验密码
        String md5InputPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5InputPassword.equals(userInfo.getPassword())) {
            throw new BusinessException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 校验是否被禁用
        if (userInfo.getStatus() == 0) {
            throw new BusinessException(ResultCodeEnum.ACCOUNT_STOP);
        }

        // 生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:store:" + token, JSONObject.toJSONString(userInfo), 30, TimeUnit.DAYS);
        return token;
    }


    /**
     * 获取当前登录用户信息
     *
     * @param token token
     * @return 用户信息Vo对象
     */
    @Override
    public UserInfoVo queryCurrentUserInfo(String token) {
        /*String userInfoJson = redisTemplate.opsForValue().get("user:store:" + token);
        if (StringUtils.isEmpty(userInfoJson)) {
            throw new BusinessException(ResultCodeEnum.LOGIN_AUTH);
        }
        UserInfo userInfo = JSONObject.parseObject(userInfoJson, UserInfo.class);*/
        // 从ThreadLocal中获取UserInfo对象
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }
}
