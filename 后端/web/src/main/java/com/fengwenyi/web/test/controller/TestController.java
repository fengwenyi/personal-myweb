package com.fengwenyi.web.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fengwenyi on 2017/7/7.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/hello")
    public ModelAndView hello() {
        ModelAndView modelAndView = new ModelAndView("hello");
        return modelAndView;
    }
}
