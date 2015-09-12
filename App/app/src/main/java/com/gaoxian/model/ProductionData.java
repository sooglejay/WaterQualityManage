package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/9/12.
 */
public class ProductionData implements Parcelable {
    private String SCKZName;
    private String SCKZCode;
    private double SCKZData;

    @Override
    public String toString() {
        return "ProductionData{" +
                "SCKZName='" + SCKZName + '\'' +
                ", SCKZCode='" + SCKZCode + '\'' +
                ", SCKZData=" + SCKZData +
                '}';
    }

    public String getSCKZName() {
        return SCKZName;
    }

    public void setSCKZName(String SCKZName) {
        this.SCKZName = SCKZName;
    }

    public String getSCKZCode() {
        return SCKZCode;
    }

    public void setSCKZCode(String SCKZCode) {
        this.SCKZCode = SCKZCode;
    }

    public double getSCKZData() {
        return SCKZData;
    }

    public void setSCKZData(double SCKZData) {
        this.SCKZData = SCKZData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.SCKZName);
        dest.writeString(this.SCKZCode);
        dest.writeDouble(this.SCKZData);
    }

    public ProductionData() {
    }

    protected ProductionData(Parcel in) {
        this.SCKZName = in.readString();
        this.SCKZCode = in.readString();
        this.SCKZData = in.readDouble();
    }

    public static final Parcelable.Creator<ProductionData> CREATOR = new Parcelable.Creator<ProductionData>() {
        public ProductionData createFromParcel(Parcel source) {
            return new ProductionData(source);
        }

        public ProductionData[] newArray(int size) {
            return new ProductionData[size];
        }
    };
}
