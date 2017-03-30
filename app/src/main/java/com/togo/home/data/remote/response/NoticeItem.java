package com.togo.home.data.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by OSX on 2017/2/16.
 */

public class NoticeItem implements Serializable ,Parcelable {
    private static final long serialVersionUID = 9177832146390176239L;
    public String title;
    public String icon;
    public String url;
    public NoticeItem(){
        //An empty method that does not need to be processed.
    }
    protected NoticeItem(Parcel in) {
        this.title = in.readString();
        this.icon = in.readString();
        this.url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.icon);
        dest.writeString(this.url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoticeItem> CREATOR = new Creator<NoticeItem>() {
        @Override
        public NoticeItem createFromParcel(Parcel in) {
            return new NoticeItem(in);
        }

        @Override
        public NoticeItem[] newArray(int size) {
            return new NoticeItem[size];
        }
    };
}
