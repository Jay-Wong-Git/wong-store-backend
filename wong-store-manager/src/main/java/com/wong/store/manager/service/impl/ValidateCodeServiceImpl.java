package com.wong.store.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.wong.store.manager.service.ValidateCodeService;
import com.wong.store.model.vo.system.ValidateCodeVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay Wong
 * @date 2023/12/22 16:35
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
        // 1.通过工具生成图片验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 10);
        String codeValue = circleCaptcha.getCode(); // 4位验证码
        String imageBase64 = circleCaptcha.getImageBase64();  // base64图片
        // 2.把验证码存储到redis
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(
                "user:login:captcha:" + key,
                codeValue,
                5,
                TimeUnit.MINUTES);
        // 3.返回ValidateCodeVo对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        return validateCodeVo;
    }
}
