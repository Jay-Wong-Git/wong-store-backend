package com.wong.store.model.vo.common;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200 , "Operation successful.") ,
    LOGIN_ERROR(201 , "Username or password incorrect."),
    VALIDATE_CODE_ERROR(202 , "Captcha incorrect.") ,
    LOGIN_AUTH(208 , "Not logged in."),
    USER_NAME_IS_EXISTS(209 , "Username already exists."),
    SYSTEM_ERROR(9999 , "Network error, try again later."),
    NODE_ERROR( 217, "This node has child nodes and cannot be deleted."),
    DATA_ERROR(204, "Data error."),
    ACCOUNT_STOP( 216, "The account has been disabled."),

    STOCK_LESS( 219, "Insufficient inventory."),

    ;

    private final Integer code ;      // 业务状态码
    private final String message ;    // 响应消息

    ResultCodeEnum(Integer code , String message) {
        this.code = code ;
        this.message = message ;
    }

}
