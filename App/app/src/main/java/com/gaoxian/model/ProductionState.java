package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/9/12.
 */
public class ProductionState implements Parcelable {

    private String SCKZName;
    private String SCKZCode;
    private int SCKZState;

    @Override
    public String toString() {
        return "ProductionState{" +
                "SCKZName='" + SCKZName + '\'' +
                ", SCKZCode='" + SCKZCode + '\'' +
                ", SCKZState=" + SCKZState +
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

    public int getSCKZState() {
        return SCKZState;
    }

    public void setSCKZState(int SCKZState) {
        this.SCKZState = SCKZState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.SCKZName);
        dest.writeString(this.SCKZCode);
        dest.writeInt(this.SCKZState);
    }

    public ProductionState() {
    }

    protected ProductionState(Parcel in) {
        this.SCKZName = in.readString();
        this.SCKZCode = in.readString();
        this.SCKZState = in.readInt();
    }

    public static final Parcelable.Creator<ProductionState> CREATOR = new Parcelable.Creator<ProductionState>() {
        public ProductionState createFromParcel(Parcel source) {
            return new ProductionState(source);
        }

        public ProductionState[] newArray(int size) {
            return new ProductionState[size];
        }
    };
}

