package com.example.scxh.news.pictureNews;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by scxh on 2016/8/15.
 */
public class PicHelper implements Parcelable{
    private String title;
    private String link;
    private String content;

    public static final Creator<PicHelper> CREATOR = new Creator<PicHelper>() {
        @Override
        public PicHelper createFromParcel(Parcel in) {
            return new PicHelper();
        }

        @Override
        public PicHelper[] newArray(int size) {
            return new PicHelper[size];
        }
    };

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(content);
    }
}
