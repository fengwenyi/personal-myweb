package com.fengwenyi.entity;

import javax.persistence.*;

/**
 * Name   : Link
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-24
 * Time   : 下午9:19
 */
@Entity
@Table(name = "xfsy_link")
public class Link {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "http")
    private String http;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "time")
    private long time;

    public Link(String name, String http, Integer weight) {
        this.name = name;
        this.http = http;
        this.weight = weight;
    }

    public Link(String name, String http) {
        this.name = name;
        this.http = http;
    }

    public Link() {
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

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
