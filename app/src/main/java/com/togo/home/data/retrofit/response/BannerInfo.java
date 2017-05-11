package com.togo.home.data.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by wangliang on 16-11-28.
 */

public class BannerInfo implements Parcelable, Serializable {
    private static final long serialVersionUID = -8072964273749515880L;

    private String icon;
    private String url;
    private String color;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.url);
        dest.writeString(this.color);
    }

    public BannerInfo() {
        // empty
    }

    protected BannerInfo(Parcel in) {
        this.icon = in.readString();
        this.url = in.readString();
        this.color = in.readString();
    }

    public static final Creator<BannerInfo> CREATOR = new Creator<BannerInfo>() {
        @Override
        public BannerInfo createFromParcel(Parcel source) {
            return new BannerInfo(source);
        }

        @Override
        public BannerInfo[] newArray(int size) {
            return new BannerInfo[size];
        }
    };
}
