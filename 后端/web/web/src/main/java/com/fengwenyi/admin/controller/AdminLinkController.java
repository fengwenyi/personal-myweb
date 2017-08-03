package com.fengwenyi.admin.controller;

import com.fengwenyi.entity.Link;
import com.fengwenyi.repository.LinkRepository;
import com.fengwenyi.result.ExceptionConstant;
import com.fengwenyi.result.Result;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Name   : AdminLinkController
 * Desc   : 友情链接
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-24
 * Time   : 下午9:18
 */
@RestController
@RequestMapping(value = "/admin/link")
public class AdminLinkController {

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

    // 增加
    @PostMapping(value = "/add")
    public String add(Link link) {
        long time = System.currentTimeMillis();
        link.setTime(time);
        linkRepository.save(link);
        Result result = new Result(ExceptionConstant.SUCCESS);
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    // 删除
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Integer id) {
        linkRepository.delete(id);
        Result result = new Result(ExceptionConstant.SUCCESS);
        Gson gson = new Gson();
        return gson.toJson(result);
    }

}
