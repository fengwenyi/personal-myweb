package com.fengwenyi.bean;

import java.util.List;

/**
 * Name   : Paging
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-07-19
 * Time   : 上午1:42
 */
public class Paging<T> {

    private Integer page;
    private List<T> content;
    private Integer totalPages;

    public Paging(Integer page, List<T> content, Integer totalPages) {
        this.page = page;
        this.content = content;
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public List<T> getContent() {
        return content;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
}
