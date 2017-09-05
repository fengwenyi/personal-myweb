package com.fengwenyi.index.controller

import com.fengwenyi.bean.CommentReplyBean
import com.fengwenyi.bean.ReplyBean
import com.fengwenyi.entity.Comment
import com.fengwenyi.entity.Customer
import com.fengwenyi.entity.Reply
import com.fengwenyi.repository.CommentRepository
import com.fengwenyi.repository.CustomerRepository
import com.fengwenyi.repository.ReplyRepository
import com.fengwenyi.result.ExceptionConstant
import com.fengwenyi.result.Result
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * WenyiFeng(xfsy2014@gmail.com)
 * 2017-08-24 下午9:28
 */

@RestController
@RequestMapping(value = "/cs")
class CommentSayController {

    @Autowired
    lateinit var commentRepository: CommentRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var replyRepository: ReplyRepository

    // 评论
    @PostMapping(value = "/comment")
    fun comment(
            @RequestParam("content") content: String,
            @RequestParam("entity_tag") entityTag: String,
            @RequestParam("user_tag") userTag: String,
            @RequestParam("phone") phone: String
    ): String? {
        val id = 0
        val time: Long = System.currentTimeMillis()
        val customer = customer(phone)
        val result: Result
        result = if (customer != null) {
            val commentObject = Comment(id, content, time, entityTag, userTag, null, null, null, null,
                    customer, null)
            val comment = commentRepository.save(commentObject)
            if (comment != null) {
                // 成功
                Result(ExceptionConstant.SUCCESS)
            } else {
                // 评论提交失败
                Result(ExceptionConstant.ERROR_CS_COMMENT_SAVE)
            }
        } else {
            // 客户注册失败
            Result(ExceptionConstant.ERROR_CS_CUSTOMER_SAVE)
        }

        return Gson().toJson(result)
    }

    // 回复
    @PostMapping(value = "/reply")
    fun reply(@RequestParam(value = "content") content: String,
              @RequestParam(value = "comment_id") commentId : Int,
              @RequestParam(value = "user_new_tag") userNewTag : String,
              @RequestParam(value = "user_old_tag") userOldTag : String,
              @RequestParam(value = "phone") phone : String) : String {

        val customer = customer(phone)
        val result : Result

        // 查询评论
        val comments = commentRepository.findAllById(commentId)
        val commentCount = comments.size
        val comment : Comment
        val id = 0
        val time = System.currentTimeMillis()
        if (commentCount > 0) {
            comment = comments[0]

            // 特别注意  一对多，一定要先保存一方，再保存多方

            val reply = Reply(id, content, time, "", "", "", "", userNewTag, userOldTag, comment, customer)
            //val replyObject = replyRepository.save(reply)

            val replies = ArrayList<Reply>()
            replies.add(reply)

            comment.replies = replies
            commentRepository.save(comment)

            customer.reply = replies
            customerRepository.save(customer)

            replyRepository.save(reply)

            result = Result(ExceptionConstant.SUCCESS)

        } else {
            // 回复对象错误
            result = Result(ExceptionConstant.ERROR_CS_REPLY_COMMENT_ENTITY_NO)
        }

        return Gson().toJson(result)
    }

    // 处理客户, 如果该客户没注册没注册 返回 客户类
    private fun customer (phone: String) : Customer {
        val id = 0
        val time: Long = System.currentTimeMillis()
        val customers = customerRepository.findAllByPhone(phone)
        val customerCount = customers.size
        return if (customerCount > 0) // 客户已注册
            customers[0]
         else //没注册
        {
            val customerObject = Customer(id, phone, time, null, null)
            customerRepository.save(customerObject)
        }
    }

    // 列表（分页） 分页 和 排序 没做
    @RequestMapping(value = "/comment_list")
    fun commentList(@RequestParam(value = "entity_tag") entityTag: String,
                    @RequestParam(value = "phone") phone: String,
                    @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
                    @RequestParam(value = "size", required = false, defaultValue = "10") size: Int,
                    @RequestParam(value = "sort", required = false, defaultValue = "desc") sort: String): String? {
        // 先根据手机号查找客户，再根据客户和评论对象找评论列表

        val sortSql = if (sort == "asc") Sort(Sort.Direction.ASC, "time")
        else Sort(Sort.Direction.DESC, "time")

        val customers = customerRepository.findAllByPhone(phone)
        var customer: Customer
        var comments: List<Comment>
        var result: Result
        if (customers.isNotEmpty()) {
            customer = customers[0]
            comments = commentRepository.findAllByCustomerAndEntityTag(customer, entityTag, sortSql)

            if (comments.isNotEmpty()) {
                result = Result(ExceptionConstant.SUCCESS)

                val datas = ArrayList<CommentReplyBean>()
                for (commentObject in comments) {
                    val cId: Int = commentObject.id
                    val cContent: String = commentObject.content
                    val cIp: String? = commentObject.ip
                    val cPosition: String? = commentObject.position
                    val cDevice: String? = commentObject.device
                    val cBrowser: String? = commentObject.browser
                    val cTime: Long = commentObject.time
                    val cUserTag: String = commentObject.user_tag
                    val cEntityTag: String = commentObject.entity_tag

                    val replyList = commentObject.replies

                    if (sort != "asc") Collections.reverse(replyList)
                    val replyListBean = ArrayList<ReplyBean>()

                    if (replyList != null) {
                        for (replyObject in replyList) {
                            val rId: Int = replyObject.id
                            val rContent: String = replyObject.content
                            val rIp: String? = replyObject.ip
                            val rPosition: String? = replyObject.position
                            val rDevice: String? = replyObject.device
                            val rBrowser: String? = replyObject.browser
                            val rTime: Long = replyObject.time
                            val rUserTagNew: String = replyObject.user_tag_new
                            val rUserTagOld: String = replyObject.user_tag_old

                            val replyBean = ReplyBean(rId, rContent, rIp, rPosition, rDevice, rBrowser, rTime, rUserTagNew,
                                    rUserTagOld)
                            replyListBean.add(replyBean)
                        }
                    }

                    val commentAndReplyBean = CommentReplyBean(cId, cContent, cIp, cPosition, cDevice, cBrowser, cTime,
                            cUserTag, cEntityTag, replyListBean)
                    datas.add(commentAndReplyBean)
                }
                result.data = datas
            } else {
                result = Result(ExceptionConstant.ERROR_CS_COMMENT_DATA_NO)
            }
        } else {
            result = Result(ExceptionConstant.ERROR_CS_CUSTOMER_DATA_NO)
        }
        return Gson().toJson(result)
    }

    // 消息提醒

    // 数据导出

    // 第三方登录
}