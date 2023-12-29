package com.wong.store.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 通用redis键值序列化配置类
 *
 * @author Jay Wong
 * @date 2023/12/29 20:28
 */
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 配置连接工厂
        template.setConnectionFactory(factory);
        // 配置键序列化器
        template.setKeySerializer(new StringRedisSerializer());
        // 配置值序列化器
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 配置哈希键序列化器
        template.setHashKeySerializer(new StringRedisSerializer());
        // 配置哈希值序列化器
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
