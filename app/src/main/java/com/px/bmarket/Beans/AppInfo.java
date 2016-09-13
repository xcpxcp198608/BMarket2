package com.px.bmarket.Beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PX on 2016/9/11.
 */
public class AppInfo {
    @SerializedName("apkName")
    private String apkName;
    @SerializedName("apkFileName")
    private String apkFileName;
    @SerializedName("apkPackageName")
    private String apkPackageName;
    @SerializedName("apkIconUrl")
    private String apkIconUrl;
    @SerializedName("apkDownloadUrl")
    private String apkDownloadUrl;
    @SerializedName("apkVersion")
    private String apkVersion;
    @SerializedName("apkSize")
    private String apkSize;
    @SerializedName("apkType")
    private String apkType;
    @SerializedName("apkLanguage")
    private String apkLanguage;
    @SerializedName("apkSummary")
    private String apkSummary;
    @SerializedName("isRecommend")
    private String isRecommend;
    @SerializedName("isDisplay")
    private String isDisplay;
    @SerializedName("apkVersionCode")
    private int apkVersionCode;
    @SerializedName("sequence")
    private int sequence;

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

    public String getApkPackageName() {
        return apkPackageName;
    }

    public void setApkPackageName(String apkPackageName) {
        this.apkPackageName = apkPackageName;
    }

    public String getApkIconUrl() {
        return apkIconUrl;
    }

    public void setApkIconUrl(String apkIconUrl) {
        this.apkIconUrl = apkIconUrl;
    }

    public String getApkDownloadUrl() {
        return apkDownloadUrl;
    }

    public void setApkDownloadUrl(String apkDownloadUrl) {
        this.apkDownloadUrl = apkDownloadUrl;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getApkSize() {
        return apkSize;
    }

    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }

    public String getApkType() {
        return apkType;
    }

    public void setApkType(String apkType) {
        this.apkType = apkType;
    }

    public String getApkLanguage() {
        return apkLanguage;
    }

    public void setApkLanguage(String apkLanguage) {
        this.apkLanguage = apkLanguage;
    }

    public String getApkSummary() {
        return apkSummary;
    }

    public void setApkSummary(String apkSummary) {
        this.apkSummary = apkSummary;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    public int getApkVersionCode() {
        return apkVersionCode;
    }

    public void setApkVersionCode(int apkVersionCode) {
        this.apkVersionCode = apkVersionCode;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "apkName='" + apkName + '\'' +
                ", apkFileName='" + apkFileName + '\'' +
                ", apkPackageName='" + apkPackageName + '\'' +
                ", apkIconUrl='" + apkIconUrl + '\'' +
                ", apkDownloadUrl='" + apkDownloadUrl + '\'' +
                ", apkVersion='" + apkVersion + '\'' +
                ", apkSize='" + apkSize + '\'' +
                ", apkType='" + apkType + '\'' +
                ", apkLanguage='" + apkLanguage + '\'' +
                ", apkSummary='" + apkSummary + '\'' +
                ", isRecommend='" + isRecommend + '\'' +
                ", isDisplay='" + isDisplay + '\'' +
                ", apkVersionCode=" + apkVersionCode +
                ", sequence=" + sequence +
                '}';
    }
}
