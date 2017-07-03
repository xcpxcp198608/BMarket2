package com.px.bmarket.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PX on 2016/9/11.
 */
public class AppInfo implements Parcelable {

    private int id;
    private int sequence;
    private String name;
    private String packageName;
    private String url;
    private String icon;
    private String size;
    private String version;
    private int code;
    private String type;
    private String language;
    private String recommend;
    private String display;
    private String summary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "id=" + id +
                ", sequence=" + sequence +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", size='" + size + '\'' +
                ", version='" + version + '\'' +
                ", code=" + code +
                ", type='" + type + '\'' +
                ", language='" + language + '\'' +
                ", recommend='" + recommend + '\'' +
                ", display='" + display + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.sequence);
        dest.writeString(this.name);
        dest.writeString(this.packageName);
        dest.writeString(this.url);
        dest.writeString(this.icon);
        dest.writeString(this.size);
        dest.writeString(this.version);
        dest.writeInt(this.code);
        dest.writeString(this.type);
        dest.writeString(this.language);
        dest.writeString(this.recommend);
        dest.writeString(this.display);
        dest.writeString(this.summary);
    }

    public AppInfo() {
    }

    protected AppInfo(Parcel in) {
        this.id = in.readInt();
        this.sequence = in.readInt();
        this.name = in.readString();
        this.packageName = in.readString();
        this.url = in.readString();
        this.icon = in.readString();
        this.size = in.readString();
        this.version = in.readString();
        this.code = in.readInt();
        this.type = in.readString();
        this.language = in.readString();
        this.recommend = in.readString();
        this.display = in.readString();
        this.summary = in.readString();
    }

    public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
}
