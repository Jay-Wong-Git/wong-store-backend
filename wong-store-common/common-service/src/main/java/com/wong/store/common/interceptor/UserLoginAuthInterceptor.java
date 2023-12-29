package com.wong.store.common.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.wong.store.model.entity.user.UserInfo;
import com.wong.store.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用于从redis中读取UserInfo数据并保存到ThreadLocal中
 *
 * @author Jay Wong
 * @date 2023/12/28 23:19
 */
public class UserLoginAuthInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从redis中获取用户信息并保存到ThreadLocal中
        String userInfoJson = redisTemplate.opsForValue().get("user:store:" + request.getHeader("token"));
        AuthContextUtil.setUserInfo(JSONObject.parseObject(userInfoJson, UserInfo.class));
        return true;
    }
}
