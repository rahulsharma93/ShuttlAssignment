package com.shuttl.assignment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rahul on 9/18/2017.
 */

public class FeedModel implements Parcelable{

    private String name;
    private String imageUrl;
    private String title;
    private String text;
    private long time;
    private String description;

    private boolean isLike=false;

    protected FeedModel(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        title = in.readString();
        text = in.readString();
        time = in.readLong();
        description = in.readString();
        isLike = in.readByte() != 0;
    }

    public static final Creator<FeedModel> CREATOR = new Creator<FeedModel>() {
        @Override
        public FeedModel createFromParcel(Parcel in) {
            return new FeedModel(in);
        }

        @Override
        public FeedModel[] newArray(int size) {
            return new FeedModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeLong(time);
        dest.writeString(description);
        dest.writeByte((byte) (isLike ? 1 : 0));
    }
}
