package com.px.bmarket.Beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PX on 2016/9/12.
 */
public class MarqueeInfo {

    @SerializedName("id")
    private int id;
    @SerializedName("content")
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MarqueeInfo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
