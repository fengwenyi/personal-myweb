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

    private long time; // 网址提交时间

    private String nickname;

    private String phone;

    private long entryTime; //录入时间

    public URLBean(String name, String url, String introduce, long time, String nickname) {
        this.name = name;
        this.url = url;
        this.introduce = introduce;
        this.time = time;
        this.nickname = nickname;
    }

    public URLBean(String name, String url, String introduce, long time, String nickname, String phone, long entryTime) {
        this.name = name;
        this.url = url;
        this.introduce = introduce;
        this.time = time;
        this.nickname = nickname;
        this.phone = phone;
        this.entryTime = entryTime;
    }
}
