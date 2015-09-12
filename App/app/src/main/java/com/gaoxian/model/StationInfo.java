package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/9/12.
 */
public class StationInfo implements Parcelable {
    private String StationName;
    private String STCD;
    private double MAPLGTD;
    private double MAPLTTD;
    private String JCinfo;
    private String CCInfo;

    @Override
    public String toString() {
        return "StationInfo{" +
                "StationName='" + StationName + '\'' +
                ", STCD='" + STCD + '\'' +
                ", MAPLGTD=" + MAPLGTD +
                ", MAPLTTD=" + MAPLTTD +
                ", JCinfo='" + JCinfo + '\'' +
                ", CCInfo='" + CCInfo + '\'' +
                '}';
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getSTCD() {
        return STCD;
    }

    public void setSTCD(String STCD) {
        this.STCD = STCD;
    }

    public double getMAPLGTD() {
        return MAPLGTD;
    }

    public void setMAPLGTD(double MAPLGTD) {
        this.MAPLGTD = MAPLGTD;
    }

    public double getMAPLTTD() {
        return MAPLTTD;
    }

    public void setMAPLTTD(double MAPLTTD) {
        this.MAPLTTD = MAPLTTD;
    }

    public String getJCinfo() {
        return JCinfo;
    }

    public void setJCinfo(String JCinfo) {
        this.JCinfo = JCinfo;
    }

    public String getCCInfo() {
        return CCInfo;
    }

    public void setCCInfo(String CCInfo) {
        this.CCInfo = CCInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.StationName);
        dest.writeString(this.STCD);
        dest.writeDouble(this.MAPLGTD);
        dest.writeDouble(this.MAPLTTD);
        dest.writeString(this.JCinfo);
        dest.writeString(this.CCInfo);
    }

    public StationInfo() {
    }

    protected StationInfo(Parcel in) {
        this.StationName = in.readString();
        this.STCD = in.readString();
        this.MAPLGTD = in.readDouble();
        this.MAPLTTD = in.readDouble();
        this.JCinfo = in.readString();
        this.CCInfo = in.readString();
    }

    public static final Parcelable.Creator<StationInfo> CREATOR = new Parcelable.Creator<StationInfo>() {
        public StationInfo createFromParcel(Parcel source) {
            return new StationInfo(source);
        }

        public StationInfo[] newArray(int size) {
            return new StationInfo[size];
        }
    };
}
