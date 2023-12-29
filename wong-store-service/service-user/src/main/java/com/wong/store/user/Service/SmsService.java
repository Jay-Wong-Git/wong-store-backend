package com.wong.store.user.Service;

/**
 * @author Jay Wong
 * @date 2023/12/28 21:52
 */
public interface SmsService {
    // 发送短信验证码
    void sendValidateCode(String phone);
}
