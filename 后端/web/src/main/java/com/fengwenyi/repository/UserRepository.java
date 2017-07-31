package com.fengwenyi.repository;

import com.fengwenyi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Name   : UserRepository
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-26
 * Time   : 下午10:33
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    // 通过手机号查询
    List<User> findAllByPhone(String phone);
}
