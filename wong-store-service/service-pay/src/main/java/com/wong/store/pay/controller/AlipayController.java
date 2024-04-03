package com.wong.store.pay.controller;

import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.pay.service.AlipayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jay Wong
 * @date 2023/12/31 21:23
 */
@Tag(name = "支付宝接口")
@Controller
@RequestMapping("/api/order/alipay")
public class AlipayController {
    @Resource
    private AlipayService alipayService;

    /**
     * 支付宝下单接口
     *
     * @param orderNo 订单编号
     * @return 支付结果信息
     */
    @Operation(summary = "支付宝下单接口")
    @GetMapping("/submitAlipay/{orderNo}")
    @ResponseBody
    public Result<String> submitAlipay(@Parameter(name = "orderNo", description = "订单号", required = true) @PathVariable(value = "orderNo") String orderNo) {
        String form = alipayService.submitAlipay(orderNo);
        return Result.build(form, ResultCodeEnum.SUCCESS);
    }
}
