package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/12.
 */
public class ChlorineDosingDataPackge implements Parcelable {
    private List<ChlorineDosingData>CDDataList;

    @Override
    public String toString() {
        return "ChlorineDosingDataPackge{" +
                "CDDataList=" + CDDataList +
                '}';
    }

    public List<ChlorineDosingData> getCDDataList() {
        return CDDataList;
    }

    public void setCDDataList(List<ChlorineDosingData> CDDataList) {
        this.CDDataList = CDDataList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.CDDataList);
    }

    public ChlorineDosingDataPackge() {
    }

    protected ChlorineDosingDataPackge(Parcel in) {
        this.CDDataList = new ArrayList<ChlorineDosingData>();
        in.readList(this.CDDataList, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<ChlorineDosingDataPackge> CREATOR = new Parcelable.Creator<ChlorineDosingDataPackge>() {
        public ChlorineDosingDataPackge createFromParcel(Parcel source) {
            return new ChlorineDosingDataPackge(source);
        }

        public ChlorineDosingDataPackge[] newArray(int size) {
            return new ChlorineDosingDataPackge[size];
        }
    };
}
