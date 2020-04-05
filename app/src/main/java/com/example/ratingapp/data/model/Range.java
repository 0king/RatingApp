package com.example.ratingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Range implements Parcelable {
    private int maxValue;
    private int minValue;

    @NonNull
    @Override
    public String toString() {
        return "[" + minValue + "-" + maxValue + "]";
    }

    private Range(int maxValue, int minValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    public static Range newInstance(int max, int min) throws Exception {
        if (min>max) throw new Exception("min cannot be > max");
        if (max>9 || min<0) throw new Exception("max, min range: 0-9");
        return new Range(max, min);
    }

    private Range(Parcel in) {
        maxValue = in.readInt();
        minValue = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(maxValue);
        dest.writeInt(minValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Range> CREATOR = new Creator<Range>() {
        @Override
        public Range createFromParcel(Parcel in) {
            return new Range(in);
        }

        @Override
        public Range[] newArray(int size) {
            return new Range[size];
        }
    };

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }
}
