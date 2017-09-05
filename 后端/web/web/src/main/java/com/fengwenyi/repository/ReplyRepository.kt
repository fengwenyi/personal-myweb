package com.fengwenyi.repository

import com.fengwenyi.entity.Reply
import org.springframework.data.jpa.repository.JpaRepository

/**
 * WenyiFeng(xfsy2014@gmail.com)
 * 2017-08-24 下午9:27
 */
interface ReplyRepository : JpaRepository<Reply, Int> {
}