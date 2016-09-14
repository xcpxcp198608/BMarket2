package com.px.bmarket.Beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PX on 2016/9/11.
 */
public class VideoInfo {
    @SerializedName("videoFileName")
    private String videoFileName;
    @SerializedName("videoUrl")
    private String videoUrl;
    @SerializedName("version")
    private int version;
    @SerializedName("md5")
    private String md5;

    public String getVideoFileName() {
        return videoFileName;
    }

    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "videoFileName='" + videoFileName + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", version=" + version +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
