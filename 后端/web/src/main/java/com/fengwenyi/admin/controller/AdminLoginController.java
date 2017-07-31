package com.fengwenyi.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Name   : AdminLoginController
 * Desc   : 后台登录
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-08
 * Time   : 下午8:33
 */
@RestController
@RequestMapping(value = "/verify")
public class AdminLoginController {

    // 登录页面
    @GetMapping(value = "/login")
    public String login() {
        return null;
    }
}
