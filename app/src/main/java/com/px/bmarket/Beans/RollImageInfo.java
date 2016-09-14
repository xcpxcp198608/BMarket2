package com.px.bmarket.Beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PX on 2016/9/11.
 */
public class RollImageInfo {
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("linkUrl")
    private String linkUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @Override
    public String toString() {
        return "RollImageInfo{" +
                "imageUrl='" + imageUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                '}';
    }
}
