package com.px.bmarket.Beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PX on 2016/9/11.
 */
public class AppMarketInfo {
    @SerializedName("apkName")
    private String apkName;
    @SerializedName("apkFileName")
    private String apkFileName;
    @SerializedName("apkFileDownloadUrl")
    private String apkFileDownloadUrl;
    @SerializedName("apkPackageName")
    private String apkPackageName;
    @SerializedName("apkUpdateInfo")
    private String apkUpdateInfo;
    @SerializedName("apkVersionCode")
    private int apkVersionCode;

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkFileName() {
        return apkFileName;
    }

    public void setApkFileName(String apkFileName) {
        this.apkFileName = apkFileName;
    }

    public String getApkFileDownloadUrl() {
        return apkFileDownloadUrl;
    }

    public void setApkFileDownloadUrl(String apkFileDownloadUrl) {
        this.apkFileDownloadUrl = apkFileDownloadUrl;
    }

    public String getApkPackageName() {
        return apkPackageName;
    }

    public void setApkPackageName(String apkPackageName) {
        this.apkPackageName = apkPackageName;
    }

    public String getApkUpdateInfo() {
        return apkUpdateInfo;
    }

    public void setApkUpdateInfo(String apkUpdateInfo) {
        this.apkUpdateInfo = apkUpdateInfo;
    }

    public int getApkVersionCode() {
        return apkVersionCode;
    }

    public void setApkVersionCode(int apkVersionCode) {
        this.apkVersionCode = apkVersionCode;
    }

    @Override
    public String toString() {
        return "AppMarketInfo{" +
                "apkName='" + apkName + '\'' +
                ", apkFileName='" + apkFileName + '\'' +
                ", apkFileDownloadUrl='" + apkFileDownloadUrl + '\'' +
                ", apkPackageName='" + apkPackageName + '\'' +
                ", apkUpdateInfo='" + apkUpdateInfo + '\'' +
                ", apkVersionCode=" + apkVersionCode +
                '}';
    }
}
