package com.fengwenyi.bean;

/**
 * Name   : ParserHtmlUrl
 * Desc   : ...
 * Use    : ...
 * Author : xfsyMrFeng
 * Date   : 2017-08-02
 * Time   : 下午7:44
 */
public class ParserHtmlUrl {

    private String name;

    private String url;

    public ParserHtmlUrl() {
    }

    public ParserHtmlUrl(String name, String url) {
        this.name = name;
        this.url = url;
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
}
