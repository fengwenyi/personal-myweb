package com.fengwenyi.admin.controller;

import com.fengwenyi.entity.Message;
import com.fengwenyi.repository.MessageRepository;
import com.fengwenyi.result.ExceptionConstant;
import com.fengwenyi.result.Result;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Name   : MessageController
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-20
 * Time   : 下午4:38
 */
@RestController
@RequestMapping(value = "/admin/message")
public class AdminMessageController {

    @Autowired
    private MessageRepository messageRepository;

    // 列表
    @GetMapping(value = "/all")
    public String findAll() {
        Result result = new Result(ExceptionConstant.SUCCESS);
        List<Message> messages = messageRepository.findAll();
        result.setData(messages);
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    // 删除
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Integer id) {
        Result result = new Result(ExceptionConstant.SUCCESS);
        messageRepository.delete(id);
        Gson gson = new Gson();
        return gson.toJson(result);
    }

}
