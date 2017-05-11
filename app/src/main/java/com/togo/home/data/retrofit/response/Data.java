package com.togo.home.data.retrofit.response;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by jinghongjun on 2/25/16.
 */
public class Data implements Serializable {
    private static final long serialVersionUID = -2471494302676931497L;
    public static final int IS_NEED_LOGIN = 1;
    public final String STATUS_DISABLE = "3";
    private int type; //type : 1  电话咨询,2    朋友圈, 3  处方药 5京东开普勒
    private String icon;
    private String url;
    private String head;
    private String title;
    private String content;
    private int needLogin;  //1需要登陆 0不需要
    private String onLine;  //1：上线，2：下线，3：停用,
    private String message;

    public int getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }

    public String getHead() {
        return head;
    }

    public String getContent() {
        return content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(int needLogin) {
        this.needLogin = needLogin;
    }

    public String getOnLine() {
        return onLine;
    }

    public void setOnLine(String onLine) {
        this.onLine = onLine;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDisable() {
        return TextUtils.equals(STATUS_DISABLE, onLine);
    }
}
