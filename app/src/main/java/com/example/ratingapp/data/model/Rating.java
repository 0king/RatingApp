package com.example.ratingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rating")
public class Rating implements Parcelable {

    @ColumnInfo(name = "value")
    private int value;

    @PrimaryKey
    @ColumnInfo(name = "created_on")
    private long createdOn;

    //restrict access
    private Rating(int value, long createdOn) {
        this.value = value;
        this.createdOn = createdOn;
    }

    public static Rating newInstance(int value, long t) throws Exception {
        if (value<0 || value>9)
            throw new Exception("Rating value should be between 0-9");
        return new Rating(value, t);
    }

    public Rating(){} //required by Room

    public Rating(Parcel in) {
        value = in.readInt();
        createdOn = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
        dest.writeLong(createdOn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
