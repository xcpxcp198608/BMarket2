package com.px.bmarket.Beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PX on 2016/9/12.
 */
public class ButtonInfo {
    @SerializedName("text")
    private String text;
    @SerializedName("url")
    private String url;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ButtonInfo{" +
                "text='" + text + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
