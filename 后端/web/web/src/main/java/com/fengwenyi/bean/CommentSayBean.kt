package com.fengwenyi.bean

/**
 * WenyiFeng(xfsy2014@gmail.com)
 * 2017-08-28 上午12:41
 */

// 返回的评论及回复字段名称示例
data class CommentReplyBean(val cId: Int = 0,
                            val cContent: String = "",
                            val cIp: String? = "",
                            val cPosition: String? = "",
                            val cDevice: String? = "",
                            val cBrowser: String? = "",
                            val cTime: Long = 1_000_000_000_000L,
                            val cUserTag: String = "",
                            val cEntityTag: String = "",
                            val replies: List<ReplyBean>? = null)

// 返回的回复字段名称示例
data class ReplyBean(val rId: Int = 0,
                     val rContent: String = "",
                     val rIp: String? = "",
                     val rPosition: String? = "",
                     val rDevice: String? = "",
                     val rBrowser: String? = "",
                     val rTime: Long = 1_000_000_000_000L,
                     val rUserTagNew: String = "",
                     val rUserTagOld: String = "")