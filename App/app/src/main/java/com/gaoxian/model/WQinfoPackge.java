package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/9/12.
 */
public class WQinfoPackge implements Parcelable {
    private List<WQinfo> WQJCList;
    private List<WQinfo> WQCCList;
    private String JCInfo;

    @Override
    public String toString() {
        return "WQinfoPackge{" +
                "WQJCList=" + WQJCList +
                ", WQCCList=" + WQCCList +
                ", JCInfo='" + JCInfo + '\'' +
                ", CCinfo='" + CCinfo + '\'' +
                '}';
    }

    public String getCCinfo() {
        return CCinfo;
    }

    public void setCCinfo(String CCinfo) {
        this.CCinfo = CCinfo;
    }

    public List<WQinfo> getWQJCList() {
        return WQJCList;
    }

    public void setWQJCList(List<WQinfo> WQJCList) {
        this.WQJCList = WQJCList;
    }

    public List<WQinfo> getWQCCList() {
        return WQCCList;
    }

    public void setWQCCList(List<WQinfo> WQCCList) {
        this.WQCCList = WQCCList;
    }

    public String getJCInfo() {
        return JCInfo;
    }

    public void setJCInfo(String JCInfo) {
        this.JCInfo = JCInfo;
    }

    private String CCinfo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(WQJCList);
        dest.writeTypedList(WQCCList);
        dest.writeString(this.JCInfo);
        dest.writeString(this.CCinfo);
    }

    public WQinfoPackge() {
    }

    protected WQinfoPackge(Parcel in) {
        this.WQJCList = in.createTypedArrayList(WQinfo.CREATOR);
        this.WQCCList = in.createTypedArrayList(WQinfo.CREATOR);
        this.JCInfo = in.readString();
        this.CCinfo = in.readString();
    }

    public static final Parcelable.Creator<WQinfoPackge> CREATOR = new Parcelable.Creator<WQinfoPackge>() {
        public WQinfoPackge createFromParcel(Parcel source) {
            return new WQinfoPackge(source);
        }

        public WQinfoPackge[] newArray(int size) {
            return new WQinfoPackge[size];
        }
    };
}
