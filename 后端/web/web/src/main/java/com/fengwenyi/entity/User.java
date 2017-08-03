package com.fengwenyi.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

/**
 * Name   : User
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-26
 * Time   : 下午10:05
 */

@Entity
@Table(name = "xfsy_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = "phone")})
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "nickname", length = 50)
    @Type(type = "java.lang.String")
    private String nickname;

    @Column(name = "phone", length = 15)
    @Type(type = "java.lang.String")
    private String phone;//用户的唯一标志

    @Column(name = "time", length = 15)
    @Type(type = "java.lang.Long")
    private long time;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<URL> urls;

    public User() {
    }

    public User(String nickname, String phone) {
        this.nickname = nickname;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<URL> getUrls() {
        return urls;
    }

    public void setUrls(List<URL> urls) {
        this.urls = urls;
    }
}
