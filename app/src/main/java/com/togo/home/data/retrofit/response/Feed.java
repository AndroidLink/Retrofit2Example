package com.togo.home.data.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jinghongjun on 6/27/16.
 */
public class Feed implements Serializable, Parcelable {
    private static final long serialVersionUID = -5100097701252467112L;

    private String display_name;
    private String display_more;
    private List<InfoListEntity> list;

    public Feed(){
        //An empty method that does not need to be processed.
    }

    protected Feed(Parcel in) {
        this.display_name = in.readString();
        this.display_more = in.readString();
        this.list = in.createTypedArrayList(InfoListEntity.CREATOR);
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getDisplay_more() {
        return display_more;
    }

    public void setDisplay_name(String displayName) {
        this.display_name = displayName;
    }

    public void setDisplay_more(String displayMore) {
        this.display_more = displayMore;
    }

    public List<InfoListEntity> getList() {
        return list;
    }

    public void setList(List<InfoListEntity> list) {
        this.list = list;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.display_name);
        dest.writeString(this.display_more);
        dest.writeTypedList(this.list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
