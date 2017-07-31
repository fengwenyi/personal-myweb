package com.fengwenyi.bean;

/**
 * Name   : URLBean
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-27
 * Time   : 上午12:22
 */
public class URLBean {

    private String name;

    private String url;

    private String introduce;

    private long time;

    private String nickname;

    public URLBean(String name, String url, String introduce, long time, String nickname) {
        this.name = name;
        this.url = url;
        this.introduce = introduce;
        this.time = time;
        this.nickname = nickname;
    }
}
