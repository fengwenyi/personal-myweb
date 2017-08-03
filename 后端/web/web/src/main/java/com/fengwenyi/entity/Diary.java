package com.fengwenyi.entity;

import javax.persistence.*;

/**
 * Created by fengwenyi on 2017/7/4.
 */
@Entity
@Table(name = "xfsy_diary")
public class Diary {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "time")
    private long time;

    @Column(name = "place")
    private String place;

    @Column(name = "content")
    private String content;

    public Diary() {
    }

    public Diary(String place, String content) {
        this.place = place;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
