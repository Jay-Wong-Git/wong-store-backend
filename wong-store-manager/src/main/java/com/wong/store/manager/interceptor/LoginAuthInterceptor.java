package com.wong.store.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.wong.store.model.entity.system.SysUser;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay Wong
 * @date 2023/12/22 20:42
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求方式
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;  // 如果是跨域预检请求，直接放行
        }

        // 获取token
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            responseNoLoginInfo(response);
            return false;
        }

        // 如果token不为空，那么此时验证token的合法性
        String userInfo = redisTemplate.opsForValue().get("user:login:" + token);
        if (StrUtil.isEmpty(userInfo)) {
            responseNoLoginInfo(response);
            return false;
        }

        // 将用户数据存储到ThreadLocal对象中
        SysUser sysUser = JSON.parseObject(userInfo, SysUser.class);
        AuthContextUtil.set(sysUser);

        // 重置redis中用户数据的有效时间
        redisTemplate.expire("user:login:" + token, 30, TimeUnit.MINUTES);

        // 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.remove();  // 移除ThreadLocal对象中的数据
    }

    // 响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Void> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
