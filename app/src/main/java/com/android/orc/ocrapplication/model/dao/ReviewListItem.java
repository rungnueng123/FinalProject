package com.android.orc.ocrapplication.model.dao;

public class ReviewListItem {

    public String facebookName, imgUrl, nameThai, star, key;

    public ReviewListItem() {
    }

    public String getFacebookName() {
        return facebookName;
    }

    public void setFacebookName(String facebookName) {
        this.facebookName = facebookName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNameThai() {
        return nameThai;
    }

    public void setNameThai(String nameThai) {
        this.nameThai = nameThai;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
