package com.android.orc.ocrapplication.dao;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by j.poobest on 21/3/2018 AD.
 */


public class MenuDao implements Parcelable {

    //เผื่ออนาคตต้องสร้าง dao เปล่าๆ
    public MenuDao() {

    }

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameThai")
    @Expose
    private String nameThai;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("quantity")
    @Expose
    private Double quantityRating;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("review")
    @Expose
    private List<CommentDao> review = null;


    protected MenuDao(Parcel in) {
        id = in.readString();
        name = in.readString();
        nameThai = in.readString();
        type = in.readString();
        description = in.readString();
        ingredient = in.readString();
        imgUrl = in.readString();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
        if (in.readByte() == 0) {
            quantityRating = null;
        } else {
            quantityRating = in.readDouble();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        review = in.createTypedArrayList(CommentDao.CREATOR);
    }

    public static final Creator<MenuDao> CREATOR = new Creator<MenuDao>() {
        @Override
        public MenuDao createFromParcel(Parcel in) {
            return new MenuDao(in);
        }

        @Override
        public MenuDao[] newArray(int size) {
            return new MenuDao[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameThai() {
        return nameThai;
    }

    public void setNameThai(String nameThai) {
        this.nameThai = nameThai;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Double getQuantityRating() {
        return quantityRating;
    }

    public void setQuantityRating(Double quantityRating) {
        this.quantityRating = quantityRating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<CommentDao> getReview() {
        return review;
    }

    public void setReview(List<CommentDao> review) {
        this.review = review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(nameThai);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(ingredient);
        dest.writeString(imgUrl);
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
        }
        if (quantityRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(quantityRating);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        dest.writeTypedList(review);
    }
}
