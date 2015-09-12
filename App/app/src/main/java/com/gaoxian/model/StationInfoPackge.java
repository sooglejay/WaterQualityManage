package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/9/12.
 */
public class StationInfoPackge implements Parcelable {
    private List<StationInfo>StationList;

    public List<StationInfo> getStationList() {
        return StationList;
    }

    public void setStationList(List<StationInfo> stationList) {
        StationList = stationList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(StationList);
    }

    public StationInfoPackge() {
    }

    protected StationInfoPackge(Parcel in) {
        this.StationList = in.createTypedArrayList(StationInfo.CREATOR);
    }

    public static final Parcelable.Creator<StationInfoPackge> CREATOR = new Parcelable.Creator<StationInfoPackge>() {
        public StationInfoPackge createFromParcel(Parcel source) {
            return new StationInfoPackge(source);
        }

        public StationInfoPackge[] newArray(int size) {
            return new StationInfoPackge[size];
        }
    };
}
