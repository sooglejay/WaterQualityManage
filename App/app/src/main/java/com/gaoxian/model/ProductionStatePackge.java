package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/9/12.
 */
public class ProductionStatePackge implements Parcelable {
    private List<ProductionState> PSDataList;

    @Override
    public String toString() {
        return "ProductionStatePackge{" +
                "PSDataList=" + PSDataList +
                '}';
    }

    public List<ProductionState> getPSDataList() {
        return PSDataList;
    }

    public void setPSDataList(List<ProductionState> PSDataList) {
        this.PSDataList = PSDataList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(PSDataList);
    }

    public ProductionStatePackge() {
    }

    protected ProductionStatePackge(Parcel in) {
        this.PSDataList = in.createTypedArrayList(ProductionState.CREATOR);
    }

    public static final Parcelable.Creator<ProductionStatePackge> CREATOR = new Parcelable.Creator<ProductionStatePackge>() {
        public ProductionStatePackge createFromParcel(Parcel source) {
            return new ProductionStatePackge(source);
        }

        public ProductionStatePackge[] newArray(int size) {
            return new ProductionStatePackge[size];
        }
    };
}
