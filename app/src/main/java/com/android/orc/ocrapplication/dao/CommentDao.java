package com.android.orc.ocrapplication.dao;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CommentDao implements Parcelable {


    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private String userName;
    @SerializedName("rate")
    @Expose
    private Float rating;
    @SerializedName("comment")
    @Expose
    private String comment;
    //    comment
    @SerializedName("date")
    @Expose
    private Date dateTime;


    public CommentDao(String userName, Float rating, String comment) {
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
    }

    protected CommentDao(Parcel in) {
        id = in.readString();
        userName = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
        comment = in.readString();
    }

    public static final Creator<CommentDao> CREATOR = new Creator<CommentDao>() {
        @Override
        public CommentDao createFromParcel(Parcel in) {
            return new CommentDao(in);
        }

        @Override
        public CommentDao[] newArray(int size) {
            return new CommentDao[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userName);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rating);
        }
        dest.writeString(comment);
    }
}
