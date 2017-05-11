package com.togo.home.data.retrofit.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jinghongjun on 2/25/16.
 */
public class Entrys implements Serializable {
    private static final long serialVersionUID = -5486197154198397050L;

    private int style; //style : 1 ,   //1行1个item; style : 2 ,   1行2个item
    private List<Data> data;

    public int getStyle() {
        return style;
    }

    public List<Data> getData() {
        return data;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
