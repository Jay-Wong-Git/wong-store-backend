package com.wong.store.user.Controller;

import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.user.Service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jay Wong
 * @date 2023/12/28 21:51
 */
@Tag(name = "短信接口")
@RestController
@RequestMapping("/api/user/sms")
public class SmsController {
    @Resource
    private SmsService smsService;

    /**
     * 发送短信验证码接口
     *
     * @param phone 手机号
     * @return 不返回数据
     */
    @Operation(summary = "发送短信验证码接口")
    @GetMapping(value = "/sendCode/{phone}")
    public Result<Void> sendValidateCode(@PathVariable String phone) {
        smsService.sendValidateCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
