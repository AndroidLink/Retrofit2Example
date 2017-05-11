package com.togo.home.data.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by OSX on 2017/2/16.
 */

public class Notices implements Serializable ,Parcelable{

    private static final long serialVersionUID = 3864770358794973832L;
    public String icon;
    public List<NoticeItem> data;
    public Notices(){
        //An empty method that does not need to be processed.
    }
    protected Notices(Parcel in) {
        this.icon = in.readString();
        this.data = in.createTypedArrayList(NoticeItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeTypedList(this.data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notices> CREATOR = new Creator<Notices>() {
        @Override
        public Notices createFromParcel(Parcel in) {
            return new Notices(in);
        }

        @Override
        public Notices[] newArray(int size) {
            return new Notices[size];
        }
    };
}
