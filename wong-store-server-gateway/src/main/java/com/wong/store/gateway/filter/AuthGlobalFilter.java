package com.wong.store.gateway.filter;

import com.alibaba.fastjson2.JSONObject;
import com.wong.store.model.entity.user.UserInfo;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 全局Filter，统一处理会员登录
 *
 * @author Jay Wong
 * @date 2023/12/28 23:10
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求路径
        String path = request.getURI().getPath();
        log.info("path {}", path);
        // 匹配需要校验登录的请求
        if (antPathMatcher.match("/api/**/auth/**", path)) {
            // 获取用户信息，如果获取不到说明没有登录
            UserInfo userInfo = this.getUserInfo(request);
            if (null == userInfo) {
                // 没有登录，返回错误信息
                return out(exchange.getResponse());
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {
        Result<Void> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        byte[] bytes = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    private UserInfo getUserInfo(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if (null != tokenList) {
            token = tokenList.get(0);
        }
        if (!StringUtils.isEmpty(token)) {
            String userInfoJson = redisTemplate.opsForValue().get("user:store:" + token);
            if (StringUtils.isEmpty(userInfoJson)) {
                return null;
            } else {
                return JSONObject.parseObject(userInfoJson, UserInfo.class);
            }
        }
        return null;
    }
}
