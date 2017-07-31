package com.fengwenyi.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Name   : JsonpAdvice
 * Desc   : 支持跨域
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-09
 * Time   : 下午10:23
 */
@ControllerAdvice(basePackages = "com.fengwenyi")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

    public JsonpAdvice() {
        super("callback", "jsoup");
    }
}
