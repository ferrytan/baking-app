package com.meetferrytan.bakingapp.data.entity;

/**
 * Created by ferrytan on 8/30/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 {
     "id": 0,
     "shortDescription": "Recipe Introduction",
     "description": "Recipe Introduction",
     "videoURL": "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
     "thumbnailURL": ""
 }
 */
public class Step implements Parcelable{
    @SerializedName("id")
    private int mId;
    @SerializedName("shortDescription")
    private String mShortDescription;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("videoURL")
    private String mVideoUrl;
    @SerializedName("thumbnailURL")
    private String mThumbnailUrl;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mShortDescription);
        dest.writeString(this.mDescription);
        dest.writeString(this.mVideoUrl);
        dest.writeString(this.mThumbnailUrl);
    }

    public Step() {
    }

    protected Step(Parcel in) {
        this.mId = in.readInt();
        this.mShortDescription = in.readString();
        this.mDescription = in.readString();
        this.mVideoUrl = in.readString();
        this.mThumbnailUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public String toString() {
        return "Step{\n" +
                "   mId= " + mId + "\n" +
                "   mShortDescription= " + mShortDescription + "\n" +
                "   mDescription= " + mDescription + "\n" +
                "   mVideoUrl= " + mVideoUrl + "\n" +
                "   mThumbnailUrl= " + mThumbnailUrl + "\n" +
                "}";
    }
}
