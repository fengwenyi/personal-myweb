package com.fengwenyi.repository;

import com.fengwenyi.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Name   : MessageRepository
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-20
 * Time   : 下午4:10
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
