package com.fengwenyi.result;

/**
 * Name   : ExceptionConstant
 * Desc   : 异常常量
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-08
 * Time   : 下午2:50
 */
public enum ExceptionConstant {
    SUCCESS(0, "Success"),
    ERROR_USER_PHONE(1, "Error: 该手机号已被注册"),
    ERROR_URL_URI(2, "Error: 该url已被收藏"),
    ERROR_UNKNOWN(-1, "Error: unknown")
    ;

    private Integer code;

    private String message;

    ExceptionConstant(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
