package com.wyxz.chaogao.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

public class GifBean implements Serializable{
    private String url;
    private String name;
    private String id;
    private int appId;
    private Bitmap bm;
    public int getAppId() {
        return appId;
    }
    public void setAppId(int appId) {
        this.appId = appId;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    
    public Bitmap getBm() {
        return bm;
    }
    public void setBm(Bitmap bm) {
        this.bm = bm;
    }
    @Override
    public String toString() {
        return "GifBean [url=" + url + ", name=" + name + ", id=" + id + ", appId=" + appId + ", bm=" + bm + "]";
    }
    
}
