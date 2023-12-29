package com.wong.store.user.Service.impl;

import com.wong.store.common.exception.BusinessException;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.user.Service.SmsService;
import com.wong.store.utils.HttpUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay Wong
 * @date 2023/12/28 21:52
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     */
    @Override
    public void sendValidateCode(String phone) {
        // 下面两行仅供测试使用
        redisTemplate.opsForValue().set("phone:code:" + phone, "1111");
        return;

        // 开发使用
        /*String code = redisTemplate.opsForValue().get("phone:code:" + phone);
        // 判断是否已经获取过验证码
        if (StringUtils.hasText(code)) {
            // 验证码申请时间超过一分钟允许再次申请
            Long lastTime = redisTemplate.getExpire("phone:code:" + phone, TimeUnit.MINUTES);
            if ((lastTime == null ? 0 : lastTime) + 1 >= 5) {
                return;
            }
        }
        // 1.生成验证码
        String validateCode = RandomStringUtils.randomNumeric(4);
        // 2.把验证码放到redis中并设置过期时间
        redisTemplate.opsForValue().set("phone:code:" + phone, validateCode, 5, TimeUnit.MINUTES);
        // 3.向手机号发送验证码
        sendSms(phone, validateCode);*/
    }

    // 发送短信方法
    private void sendSms(String phone, String code) {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "080ed235078a47a881ca0a4360da35d6";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:" + code);
        bodys.put("template_id", "CST_ptdie100");  //该模板为调试接口专用，短信下发有受限制，调试成功后请联系客服报备专属模板
        bodys.put("phone_number", phone);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
