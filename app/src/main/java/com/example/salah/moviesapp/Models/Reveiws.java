package com.example.albheryyyyuhckfjhdgf.finalproject.Models;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 17/09/2016.
 */
public class Reveiws {
    String id;
    String auther;
    String content;
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Reveiws() {
    }

    public Reveiws(String auther, String content, String id, String url) {

        this.auther = auther;
        this.content = content;
        this.id = id;
        this.url=url;
    }

}