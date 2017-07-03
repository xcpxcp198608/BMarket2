package com.px.bmarket.Beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PX on 2016/9/12.
 */
public class ButtonInfo {
    private int id;
    private String text;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
                "id=" + id +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
