package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/9/12.
 */
public class WQinfo implements Parcelable {
    private double WQMonitorData;
    private String ParameterName;
    private String ParamterCode;
    private String Unit;

    @Override
    public String toString() {
        return "WQinfo{" +
                "WQMonitorData=" + WQMonitorData +
                ", ParameterName='" + ParameterName + '\'' +
                ", ParamterCode='" + ParamterCode + '\'' +
                ", Unit='" + Unit + '\'' +
                '}';
    }

    public double getWQMonitorData() {
        return WQMonitorData;
    }

    public void setWQMonitorData(double WQMonitorData) {
        this.WQMonitorData = WQMonitorData;
    }

    public String getParameterName() {
        return ParameterName;
    }

    public void setParameterName(String parameterName) {
        ParameterName = parameterName;
    }

    public String getParamterCode() {
        return ParamterCode;
    }

    public void setParamterCode(String paramterCode) {
        ParamterCode = paramterCode;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.WQMonitorData);
        dest.writeString(this.ParameterName);
        dest.writeString(this.ParamterCode);
        dest.writeString(this.Unit);
    }

    public WQinfo() {
    }

    protected WQinfo(Parcel in) {
        this.WQMonitorData = in.readDouble();
        this.ParameterName = in.readString();
        this.ParamterCode = in.readString();
        this.Unit = in.readString();
    }

    public static final Creator<WQinfo> CREATOR = new Creator<WQinfo>() {
        public WQinfo createFromParcel(Parcel source) {
            return new WQinfo(source);
        }

        public WQinfo[] newArray(int size) {
            return new WQinfo[size];
        }
    };
}
