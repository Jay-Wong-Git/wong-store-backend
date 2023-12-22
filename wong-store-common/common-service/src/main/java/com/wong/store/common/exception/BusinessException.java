package com.wong.store.common.exception;

import com.wong.store.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * @author Jay Wong
 * @date 2023/12/22 15:58
 */
// 自定义异常类
@Data
public class BusinessException extends RuntimeException {
    private Integer code;          // 错误状态码
    private String message;        // 错误消息
    private ResultCodeEnum resultCodeEnum;     // 封装错误状态码和错误消息

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
