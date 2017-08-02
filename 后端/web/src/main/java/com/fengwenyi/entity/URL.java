package com.fengwenyi.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Name   : URL
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-26
 * Time   : 下午10:05
 */

@Entity
@Table(name = "xfsy_url",
        uniqueConstraints = {@UniqueConstraint(columnNames = "url")})
public class URL {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @Type(type = "java.lang.String")
    private String name; // url名字

    @Column(name = "url")
    @Type(type = "text")
    private String url; // url

    @Column(name = "introduce")
    @Type(type = "java.lang.String")
    private String introduce; // 介绍

    @Column(name = "time", length = 15)
    @Type(type = "java.lang.Long")
    private long time; // 加入时间

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; // 用户

    public URL() {
    }

    public URL(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public URL(String name, String url, String introduce) {
        this.name = name;
        this.url = url;
        this.introduce = introduce;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
