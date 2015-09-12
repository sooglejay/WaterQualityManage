package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/9/12.
 */
public class ChlorineDosingStatePackge implements Parcelable {
    public List<ChlorineDosingState> getCDStateList() {
        return CDStateList;
    }

    @Override
    public String toString() {
        return "ChlorineDosingStatePackge{" +
                "CDStateList=" + CDStateList +
                '}';
    }

    public void setCDStateList(List<ChlorineDosingState> CDStateList) {
        this.CDStateList = CDStateList;
    }

    private List<ChlorineDosingState>CDStateList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(CDStateList);
    }

    public ChlorineDosingStatePackge() {
    }

    protected ChlorineDosingStatePackge(Parcel in) {
        this.CDStateList = in.createTypedArrayList(ChlorineDosingState.CREATOR);
    }

    public static final Parcelable.Creator<ChlorineDosingStatePackge> CREATOR = new Parcelable.Creator<ChlorineDosingStatePackge>() {
        public ChlorineDosingStatePackge createFromParcel(Parcel source) {
            return new ChlorineDosingStatePackge(source);
        }

        public ChlorineDosingStatePackge[] newArray(int size) {
            return new ChlorineDosingStatePackge[size];
        }
    };
}
