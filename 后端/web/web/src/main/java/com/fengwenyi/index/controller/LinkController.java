package com.fengwenyi.index.controller;

import com.fengwenyi.entity.Link;
import com.fengwenyi.repository.LinkRepository;
import com.fengwenyi.result.ExceptionConstant;
import com.fengwenyi.result.Result;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Name   : LinkController
 * Desc   : 友情链接
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-24
 * Time   : 下午9:18
 */
@RestController
@RequestMapping(value = "/link")
public class LinkController {

    @Autowired
    LinkRepository linkRepository;

    // 所有
    @GetMapping(value = "/all")
    public String all() {
        Result result = new Result(ExceptionConstant.SUCCESS);
        List<Link> links = linkRepository.findAll();
        result.setData(links);
        Gson gson = new Gson();
        return gson.toJson(result);
    }

}
