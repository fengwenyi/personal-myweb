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

    // 300-400 data
    ERROR_NO_DATA(300, "Error: 暂无数据"),

    // 500 - 1000 评说系统异常
    ERROR_CS_CUSTOMER_SAVE(500, "Error: 客户信息保存出错"),
    ERROR_CS_COMMENT_SAVE(501, "Error: 评论保存出错"),
    ERROR_CS_COMMENT_DATA_NO(502, "Error: 暂无评论数据"),
    ERROR_CS_CUSTOMER_DATA_NO(503, "Error: 客户信息不存在"),
    ERROR_CS_REPLY_COMMENT_ENTITY_NO(504, "Error: 回复评论ID不正确"),

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
