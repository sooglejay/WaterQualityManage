package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/9/12.
 */
public class ProductionDataPackge implements Parcelable {
    private List<ProductionData> PDDataList;

    public List<ProductionData> getPDDataList() {
        return PDDataList;
    }

    @Override
    public String toString() {
        return "ProductionDataPackge{" +
                "PDDataList=" + PDDataList +
                '}';
    }

    public void setPDDataList(List<ProductionData> PDDataList) {
        this.PDDataList = PDDataList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(PDDataList);
    }

    public ProductionDataPackge() {
    }

    protected ProductionDataPackge(Parcel in) {
        this.PDDataList = in.createTypedArrayList(ProductionData.CREATOR);
    }

    public static final Parcelable.Creator<ProductionDataPackge> CREATOR = new Parcelable.Creator<ProductionDataPackge>() {
        public ProductionDataPackge createFromParcel(Parcel source) {
            return new ProductionDataPackge(source);
        }

        public ProductionDataPackge[] newArray(int size) {
            return new ProductionDataPackge[size];
        }
    };
}
