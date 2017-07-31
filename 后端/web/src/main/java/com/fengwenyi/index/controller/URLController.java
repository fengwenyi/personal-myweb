package com.fengwenyi.index.controller;

import com.fengwenyi.bean.URLBean;
import com.fengwenyi.entity.URL;
import com.fengwenyi.entity.User;
import com.fengwenyi.repository.URLRepository;
import com.fengwenyi.repository.UserRepository;
import com.fengwenyi.result.ExceptionConstant;
import com.fengwenyi.result.Result;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Name   : URLController
 * Desc   : 主要负责URL
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-26
 * Time   : 下午10:38
 */
@RestController
@RequestMapping(value = "/url")
public class URLController {

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private UserRepository userRepository;

    // 添加
    @PostMapping(value = "/add")
    public String add(@RequestParam(value = "name") String name,
                      @RequestParam(value = "url") String uri,
                      @RequestParam(value = "introduce") String introduce,
                      @RequestParam(value = "nickname") String nickname,
                      @RequestParam(value = "phone") String phone) {
        long time = System.currentTimeMillis();
        URL url = new URL(name, uri, introduce);
        url.setTime(time);
        User user = new User(nickname, phone);
        Result result;
        // 首先 看url是否已经存在，如果存在则返回是吧，否则继续
        List<URL> urls = urlRepository.findAllByUrl(uri);
        if (urls.size() > 0) {
            result = new Result(ExceptionConstant.ERROR_URL_URI);
        } else {
            result = new Result(ExceptionConstant.SUCCESS);
            //查看user 是否存在
            List<User> users = userRepository.findAllByPhone(phone);
            if (users.size() > 0) //表示已存在
            {
                //保存URL即可
                url.setUser(users.get(0));
                urlRepository.save(url);
            } else //手机号还不存在
            {
                //先保存用户 再保存url
                user.setTime(time);
                url.setUser(userRepository.save(user));
                urlRepository.save(url);
            }
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    //
    @GetMapping(value = "/all")
    public String all() {
        Sort sort = new Sort(Sort.Direction.DESC, "time");
        List<URL> urls = urlRepository.findAll();

        List<URLBean> urlBeans = new ArrayList<>();
        for (URL url: urls) {
            URLBean urlBean = new URLBean(url.getName(), url.getUrl(), url.getIntroduce(), url.getTime(), url.getUser().getNickname());
            urlBeans.add(urlBean);
        }

        Result result = new Result(ExceptionConstant.SUCCESS);
        Gson gson = new Gson();
        result.setData(urlBeans);
        return gson.toJson(result);
    }
}
