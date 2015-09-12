package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/9/12.
 */
public class ChlorineDosingData implements Parcelable {

    private String JLJYName;
    private String JLJYCode;
    private String JLJYData;

    @Override
    public String toString() {
        return "ChlorineDosingData{" +
                "JLJYName='" + JLJYName + '\'' +
                ", JLJYCode='" + JLJYCode + '\'' +
                ", JLJYData='" + JLJYData + '\'' +
                '}';
    }

    public String getJLJYName() {
        return JLJYName;
    }

    public void setJLJYName(String JLJYName) {
        this.JLJYName = JLJYName;
    }

    public String getJLJYCode() {
        return JLJYCode;
    }

    public void setJLJYCode(String JLJYCode) {
        this.JLJYCode = JLJYCode;
    }

    public String getJLJYData() {
        return JLJYData;
    }

    public void setJLJYData(String JLJYData) {
        this.JLJYData = JLJYData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.JLJYName);
        dest.writeString(this.JLJYCode);
        dest.writeString(this.JLJYData);
    }

    public ChlorineDosingData() {
    }

    protected ChlorineDosingData(Parcel in) {
        this.JLJYName = in.readString();
        this.JLJYCode = in.readString();
        this.JLJYData = in.readString();
    }

    public static final Parcelable.Creator<ChlorineDosingData> CREATOR = new Parcelable.Creator<ChlorineDosingData>() {
        public ChlorineDosingData createFromParcel(Parcel source) {
            return new ChlorineDosingData(source);
        }

        public ChlorineDosingData[] newArray(int size) {
            return new ChlorineDosingData[size];
        }
    };
}
