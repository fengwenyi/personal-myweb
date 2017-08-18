package com.fengwenyi.repository;

import com.fengwenyi.entity.URL;
import com.fengwenyi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Name   : URLRepository
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-26
 * Time   : 下午10:32
 */
public interface URLRepository extends JpaRepository<URL, Integer> {

    // 通过url进行查找
    List<URL> findAllByUrl(String url);

    // 通过user查询用户
    List<URL> findAllByUser(User user);

    // 根据标题 和 介绍进行搜索
    @Query(value = "select url from URL url where url.name like :key or url.introduce like :key")
    List<URL> findAllByNameLikeOrIntroduceLike(@Param(value = "key") String key);

}
