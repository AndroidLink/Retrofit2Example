package com.togo.home.data.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by OSX on 2017/2/17.
 */

public class NetHosDiseases implements Serializable, Parcelable {
    private static final long serialVersionUID = -8701033032457375919L;
    private String diseases;
    private String url;

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NetHosDiseases() {

    }

    protected NetHosDiseases(Parcel in) {
        this.diseases = in.readString();
        this.url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.diseases);
        dest.writeString(this.url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NetHosDiseases> CREATOR = new Creator<NetHosDiseases>() {
        @Override
        public NetHosDiseases createFromParcel(Parcel in) {
            return new NetHosDiseases(in);
        }

        @Override
        public NetHosDiseases[] newArray(int size) {
            return new NetHosDiseases[size];
        }
    };
}
