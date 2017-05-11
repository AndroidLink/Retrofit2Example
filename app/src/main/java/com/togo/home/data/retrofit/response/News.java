package com.togo.home.data.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jinghongjun on 6/27/16.
 */
public class News implements Serializable, Parcelable {

    private static final long serialVersionUID = 8451371383287551305L;

    private String name;    //新闻标题（最新的公告咨询）
    private String url;     //公告咨询API接口
    private String is_new;  //是否显示new标签，0不显示，1显示"

    public News() {
        //An empty method that does not need to be processed.
    }

    protected News(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.is_new = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIs_new(String isNew) {
        this.is_new = isNew;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.is_new);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
