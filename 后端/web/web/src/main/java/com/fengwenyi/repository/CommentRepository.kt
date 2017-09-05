package com.fengwenyi.repository

import com.fengwenyi.entity.Comment
import com.fengwenyi.entity.Customer
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * WenyiFeng(xfsy2014@gmail.com)
 * 2017-08-24 下午9:26
 */
interface CommentRepository : JpaRepository<Comment, Int> {

    @Query(value = "select comment from Comment comment where comment.customer = :customer and comment.entity_tag = :entityTag")
    fun findAllByCustomerAndEntityTag(@Param(value = "customer") customer : Customer,
                                      @Param(value = "entityTag") entityTag : String,
                                      sort : Sort) : List<Comment>

    fun findAllById(id : Int) : List<Comment>
}