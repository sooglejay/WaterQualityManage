package com.gaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/9/12.
 */
public class ChlorineDosingState implements Parcelable {
    private String JLJYName;
    private String JLJYCode;
    private int JLJYState;

    @Override
    public String toString() {
        return "ChlorineDosingState{" +
                "JLJYName='" + JLJYName + '\'' +
                ", JLJYCode='" + JLJYCode + '\'' +
                ", JLJYState=" + JLJYState +
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

    public int getJLJYState() {
        return JLJYState;
    }

    public void setJLJYState(int JLJYState) {
        this.JLJYState = JLJYState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.JLJYName);
        dest.writeString(this.JLJYCode);
        dest.writeInt(this.JLJYState);
    }

    public ChlorineDosingState() {
    }

    protected ChlorineDosingState(Parcel in) {
        this.JLJYName = in.readString();
        this.JLJYCode = in.readString();
        this.JLJYState = in.readInt();
    }

    public static final Parcelable.Creator<ChlorineDosingState> CREATOR = new Parcelable.Creator<ChlorineDosingState>() {
        public ChlorineDosingState createFromParcel(Parcel source) {
            return new ChlorineDosingState(source);
        }

        public ChlorineDosingState[] newArray(int size) {
            return new ChlorineDosingState[size];
        }
    };
}
