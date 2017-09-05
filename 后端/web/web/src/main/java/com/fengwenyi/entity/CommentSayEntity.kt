package com.fengwenyi.entity

import org.hibernate.annotations.Type

import javax.persistence.*


/**
 * WenyiFeng(xfsy2014@gmail.com)
 * 2017-08-23 下午8:10
 */

// 评论
@Entity
@Table(
        name = "xfsy_comment"
)
class Comment (

        @Id
        @GeneratedValue
        @Column(name = "id")
        val id : Int = 0,             // 评论编号

        @Column(name = "content")
        @Type(type = "text")
        val content : String = "",     // 内容

        @Column(name = "time", length = 13)
        val time : Long = 1_000_000_000_000L,          // 时间

        @Column(name = "entity_tag")
        val entity_tag : String = "",  // 评论的对象的标志（建议用ID）

        @Column(name = "user_tag")
        val user_tag : String = "",    // 评论者的标志

        @Column(name = "ip", length = 15)
        val ip : String? = "",          // ip

        @Column(name = "position")
        val position : String? = "",    // 位置

        @Column(name = "device")
        val device : String? = "",      // 设备

        @Column(name = "browser")
        val browser : String? = "",     // 浏览器

        @ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        @JoinColumn(name = "customer_id", referencedColumnName = "id")
        val customer: Customer? = null,   // 客户

        @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        @JoinColumn(name = "comment_id")
        var replies: List<Reply>? = null  // 回复
)

// 回复
@Entity
@Table(
        name = "xfsy_reply"
)
class Reply (

        @Id
        @GeneratedValue
        @Column(name = "id")
        val id : Int = 0,              // 回复编号

        @Column(name = "content")
        @Type(type = "text")
        val content: String = "",       // 内容

        @Column(name = "time", length = 13)
        val time: Long = 1_000_000_000_000L,            // 时间

        @Column(name = "ip", length = 15)
        val ip: String? = "",            // ip

        @Column(name = "position")
        val position: String? = "",      // 位置

        @Column(name = "device")
        val device: String? = "",        // 设备

        @Column(name = "browser")
        val browser: String? = "",       // 浏览器

        @Column(name = "user_tag_new")
        val user_tag_new : String = "", // 回复者

        @Column(name = "user_tag_old")
        val user_tag_old : String = "", // 被回复者

        @ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        @JoinColumn(name = "comment_id", referencedColumnName = "id")
        val comment : Comment? = null,     // 评论

        @ManyToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        @JoinColumn(name = "customer_id", referencedColumnName = "id")
        val customer: Customer? = null     // 客户
)

// 客户
@Entity
@Table(
        name = "xfsy_customer",
        uniqueConstraints = arrayOf(UniqueConstraint(columnNames = arrayOf("phone")))
)
class Customer (

        @Id
        @GeneratedValue
        @Column(name = "id")
        val id: Int = 0,          // 客户编号

        @Column(name = "phone", length = 11)
        val phone: String = "",    // 手机

        @Column(name = "time", length = 13)
        val time: Long = 1_000_000_000_000L,        // 第一次提交时间

        @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id")
        val comments: List<Comment>? = null, // 评论

        @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id")
        var reply: List<Reply>? = null      // 回复
)