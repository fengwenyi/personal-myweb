package com.fengwenyi.repository

import com.fengwenyi.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

/**
 * WenyiFeng(xfsy2014@gmail.com)
 * 2017-08-24 下午9:24
 */
interface CustomerRepository : JpaRepository<Customer, Int> {

    fun findAllByPhone(phone : String) : List<Customer>
}