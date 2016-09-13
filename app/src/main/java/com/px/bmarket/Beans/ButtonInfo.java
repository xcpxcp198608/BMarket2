package com.px.bmarket.Beans;

/**
 * Created by PX on 2016/9/12.
 */
public class ButtonInfo {
    private String text;
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
