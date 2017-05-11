package com.togo.home.data.retrofit.response;

/**
 * Created by shangsong on 17-1-17.
 */

public class NimAccount {
    //云信ID
    private String accid;
    //云信token
    private String token;
    //云信name
    private String name;
    //云信头像路径
    private String icon;
    //小步ID
    private String xbid;

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getXbid() {
        return xbid;
    }

    public void setXbid(String xbid) {
        this.xbid = xbid;
    }
}
