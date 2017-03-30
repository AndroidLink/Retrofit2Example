package com.togo.home.data.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by OSX on 2017/2/17.
 */

public class NetHosButtonItem implements Serializable, Parcelable {
    private static final long serialVersionUID = -579873332883265345L;
    private String style;
    private List<NetHosButtonItemInfo> data;
    public NetHosButtonItem(){

    }
    public String getStyle() {
        return style;
    }

    public List<NetHosButtonItemInfo> getData() {
        return data;
    }

    protected NetHosButtonItem(Parcel in) {
        this.style = in.readString();
        this.data = in.createTypedArrayList(NetHosButtonItemInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.style);
        dest.writeTypedList(this.data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NetHosButtonItem> CREATOR = new Creator<NetHosButtonItem>() {
        @Override
        public NetHosButtonItem createFromParcel(Parcel in) {
            return new NetHosButtonItem(in);
        }

        @Override
        public NetHosButtonItem[] newArray(int size) {
            return new NetHosButtonItem[size];
        }
    };

    public void setStyle(String style) {
        this.style = style;
    }

    public void setData(List<NetHosButtonItemInfo> data) {
        this.data = data;
    }
}
