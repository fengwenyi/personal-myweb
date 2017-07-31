package com.fengwenyi.result;

/**
 * Created by fengwenyi on 2017/7/4.
 */
public class Result {

    private Integer code;
    private String msg;
    private Object data;

    public Result(ExceptionConstant exceptionConstant) {
        this.code = exceptionConstant.getCode();
        this.msg = exceptionConstant.getMessage();
    }

    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
