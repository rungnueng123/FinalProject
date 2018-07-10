package com.android.orc.ocrapplication.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RUNGNUENG on 13/4/2561.
 */

public class FavoriteItemDao implements Parcelable {

    public String nameThai;
    public String description;
    public String ingredient;
    public String imgUrl;

    public FavoriteItemDao(){

    }

    protected FavoriteItemDao(Parcel in) {
        nameThai = in.readString();
        description = in.readString();
        ingredient = in.readString();
        imgUrl = in.readString();
    }

    public static final Creator<FavoriteItemDao> CREATOR = new Creator<FavoriteItemDao>() {
        @Override
        public FavoriteItemDao createFromParcel(Parcel in) {
            return new FavoriteItemDao(in);
        }

        @Override
        public FavoriteItemDao[] newArray(int size) {
            return new FavoriteItemDao[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameThai);
        dest.writeString(description);
        dest.writeString(ingredient);
        dest.writeString(imgUrl);
    }
}
