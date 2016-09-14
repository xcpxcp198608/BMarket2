package com.px.bmarket.Beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PX on 2016/9/12.
 */
public class MarqueeInfo {
    @SerializedName("text")
    private String text;
    @SerializedName("colorR")
    private int colorR;
    @SerializedName("colorG")
    private int colorG;
    @SerializedName("colorB")
    private int colorB;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColorR() {
        return colorR;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    @Override
    public String toString() {
        return "MarqueeInfo{" +
                "text='" + text + '\'' +
                ", colorR=" + colorR +
                ", colorG=" + colorG +
                ", colorB=" + colorB +
                '}';
    }
}
