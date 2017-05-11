package com.togo.home.data.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by OSX on 2017/2/17.
 */

public class NetHosButtonItemInfo implements Serializable, Parcelable {
    private static final long serialVersionUID = -327736473419354742L;

    public NetHosButtonItemInfo() {

    }

    private String icon;
    private String title;
    private String sub_title;
    private String content;
    private String content_icon;
    private String type;

    public String getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public String getContent() {
        return content;
    }

    public String getContent_icon() {
        return content_icon;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContent_icon(String content_icon) {
        this.content_icon = content_icon;
    }

    protected NetHosButtonItemInfo(Parcel in) {
        this.icon = in.readString();
        this.title = in.readString();
        this.sub_title = in.readString();
        this.content = in.readString();
        this.content_icon = in.readString();
        this.type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.title);
        dest.writeString(this.sub_title);
        dest.writeString(this.content);
        dest.writeString(this.content_icon);
        dest.writeString(this.type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NetHosButtonItemInfo> CREATOR = new Creator<NetHosButtonItemInfo>() {
        @Override
        public NetHosButtonItemInfo createFromParcel(Parcel in) {
            return new NetHosButtonItemInfo(in);
        }

        @Override
        public NetHosButtonItemInfo[] newArray(int size) {
            return new NetHosButtonItemInfo[size];
        }
    };
}
