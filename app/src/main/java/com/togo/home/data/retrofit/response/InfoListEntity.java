package com.togo.home.data.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shangsong on 15-12-18.
 */
public class InfoListEntity implements Parcelable {
    /**
     * 1图文  2大图  3文字
     */
    public static final int FEED_ITEM_STYLE_IMAGE_AND_TEXT = 1;
    public static final int FEED_ITEM_STYLE_ONLY_IMAGE = 2;
    public static final int FEED_ITEM_STYLE_ONLY_TEXT = 3;
    public static final int FEED_ITEM_STYLE_COUNT = 3;

    private String icon;
    private String name;
    private String func;
    //跳转类型
    private String type;
    private String sub_title;
    //医生工具和国际频道h5需要的参数
    private String idPath;
    private String url;
    //未读消息数
    private String news_unread_number;
    private int defaultImageResId;
    //发现功能，每个item的显示类型,从1开始
    private int feed_style;
    //发现功能，日期
    private String publish_time;

    private boolean redPointDisplay = false;

    private String tip;

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSub_title(String subTitle) {
        this.sub_title = subTitle;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getFunc() {
        return func;
    }

    public String getType() {
        return type;
    }

    public String getSub_title() {
        return sub_title;
    }

    public String getNews_unread_number() {
        return news_unread_number;
    }

    public void setNews_unread_number(String newsUnreadNumber) {
        this.news_unread_number = newsUnreadNumber;
    }

    public int getDefaultImageResId() {
        return defaultImageResId;
    }

    public void setDefaultImageResId(int defaultImageResId) {
        this.defaultImageResId = defaultImageResId;
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFeed_style() {
        return feed_style;
    }

    public void setFeed_style(int feedStyle) {
        this.feed_style = feedStyle;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publishTime) {
        this.publish_time = publishTime;
    }


    public boolean isRedPointDisplay() {
        return redPointDisplay;
    }

    public void setRedPointDisplay(boolean redPointDisplay) {
        this.redPointDisplay = redPointDisplay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTip() {
        return tip;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.name);
        dest.writeString(this.func);
        dest.writeString(this.type);
        dest.writeString(this.sub_title);
        dest.writeString(this.idPath);
        dest.writeString(this.url);
        dest.writeString(this.news_unread_number);
        dest.writeInt(this.defaultImageResId);
        dest.writeInt(this.feed_style);
        dest.writeString(this.publish_time);
        dest.writeByte((byte) (this.redPointDisplay ? 1 : 0));
    }

    public InfoListEntity() {
        //instantiation class without param
    }

    protected InfoListEntity(Parcel in) {
        this.icon = in.readString();
        this.name = in.readString();
        this.func = in.readString();
        this.type = in.readString();
        this.sub_title = in.readString();
        this.idPath = in.readString();
        this.url = in.readString();
        this.news_unread_number = in.readString();
        this.defaultImageResId = in.readInt();
        this.feed_style = in.readInt();
        this.publish_time = in.readString();
        this.redPointDisplay = in.readByte() != 0;
    }

    public static final Creator<InfoListEntity> CREATOR = new Creator<InfoListEntity>() {
        public InfoListEntity createFromParcel(Parcel source) {
            return new InfoListEntity(source);
        }

        public InfoListEntity[] newArray(int size) {
            return new InfoListEntity[size];
        }
    };

    public boolean isImageAndTextStyle() {
        return FEED_ITEM_STYLE_IMAGE_AND_TEXT == feed_style;
    }

    public boolean isOnlyImageStyle() {
        return FEED_ITEM_STYLE_ONLY_IMAGE == feed_style;
    }

    public boolean isOnlyTextStyle() {
        return FEED_ITEM_STYLE_ONLY_TEXT == feed_style;
    }

    //判断是否是发现的item
    public boolean isFeedEntitiy() {
        return FEED_ITEM_STYLE_IMAGE_AND_TEXT <= feed_style && FEED_ITEM_STYLE_ONLY_TEXT >= feed_style;
    }

}

