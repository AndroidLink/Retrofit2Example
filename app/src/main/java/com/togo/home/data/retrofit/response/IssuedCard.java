package com.togo.home.data.retrofit.response;

import java.io.Serializable;

/**
 * Created by jinghongjun on 1/29/16.
 */
public class IssuedCard implements Serializable {

    private static final long serialVersionUID = -1843112328700941209L;

    private int type;
    private String icon;
    private String head;
    private String title;
    private String content;

    public String getIcon() {
        return icon;
    }

    public String getHead() {
        return head;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
