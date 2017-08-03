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

    // 0 成功
    SUCCESS(0, "Success"),
    // 100-200 user错误
    ERROR_USER_PHONE(100, "Error: 该手机号已被注册"),
    ERROR_USER_NO_PHONE(101, "Error: 你还没有推荐过哦"),

    // 200-300 url错误
    ERROR_URL_URI(200, "Error: 该url已被收藏"),
    ERROR_IMPORT_URL(201, "Error: 没有读取到要导入URL"),

    // 负数 未知错误
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
