package com.fengwenyi.index.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


/**
 * WenyiFeng(xfsy2014@gmail.com)
 * 2017-08-18 下午10:22
 */

@RestController
@RequestMapping(value = "/index")
class IndexController {

    val DEF_CHATSET = "UTF-8"
    val userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36"

    @GetMapping(value = "/bg")
    fun getIndexBg() : String? {

        val http = "http://guolin.tech/api/bing_pic"

        return net(http, null, null)
    }

    @Throws(Exception::class)
    fun net(strUrl: String, params: Map<*, *>?, method: String?): String? {
        var strUrl = strUrl
        var conn: HttpURLConnection? = null
        var reader: BufferedReader? = null
        var rs: String? = null
        try {
            val sb = StringBuffer()
            if (method == null || method == "GET") {
                if (params == null) {
                    strUrl = strUrl
                } else {
                    strUrl = strUrl + "?" + urlencode(params)
                }
            }
            val url = URL(strUrl)
            conn = url.openConnection() as HttpURLConnection
            if (method == null || method == "GET") {
                conn.requestMethod = "GET"
            } else {
                conn.requestMethod = "POST"
                conn.doOutput = true
            }
            conn.setRequestProperty("User-agent", userAgent)
            conn.useCaches = false
            conn.connectTimeout = 5000
            conn.readTimeout = 3000
            conn.instanceFollowRedirects = false
            conn.connect()
            if (params != null && method == "POST") {
                try {
                    val out = DataOutputStream(conn.outputStream)
                    out.writeBytes(urlencode(params))
                } catch (e: Exception) {
                    // TODO: handle exception
                }

            }
            val input = conn.inputStream
            reader = BufferedReader(InputStreamReader(input, DEF_CHATSET))
            /*
            var strRead: String? = null
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead)
            }
            */
            while (true) {
                var line : String ? = reader.readLine()
                line?: break
                sb.append(line)
            }
            reader.close()
            rs = sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                reader.close()
            }
            if (conn != null) {
                conn.disconnect()
            }
        }
        return rs
    }

    fun urlencode(data: Map<*, *>?): String {
        val sb = StringBuilder()
        if (data != null) {
            for ((key, value) in data) {
                try {
                    sb.append(key).append("=").append(URLEncoder.encode(value.toString() + "", "UTF-8")).append("&")
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

            }
        }
        return sb.toString()
    }
}
