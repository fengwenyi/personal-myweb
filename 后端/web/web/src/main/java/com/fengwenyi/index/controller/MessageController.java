package com.fengwenyi.index.controller;

import com.fengwenyi.entity.Message;
import com.fengwenyi.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Name   : MessageController
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-20
 * Time   : 下午4:18
 */
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    //留言
    @PostMapping(value = "/add")
    public void add(Message message) {
        long time = System.currentTimeMillis();
        message.setTime(time);
        messageRepository.save(message);
    }
}
