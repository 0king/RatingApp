package com.example.ratingapp.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.example.ratingapp.data.db.RatingDao;
import com.example.ratingapp.data.db.RatingRoomDatabase;
import com.example.ratingapp.data.model.Range;
import com.example.ratingapp.data.model.Rating;

import java.util.List;

public enum MainRepo implements IRepo {
    INSTANCE; //singleton

    private static final String SHARED_PREFS_NAME = "rating_prefs";

    private static final String RATING_VALUE = "RATING_VALUE";
    private static final String RATING_TIME = "RATING_TIME";

    private static final String RANGE_MAX = "RANGE_MAX";
    private static final String RANGE_MIN = "RANGE_MIN";

    private LiveData<List<Rating>> mAllRatings = null;
    private RatingDao mRatingDao = null;

    @Override
    public Rating getCurrentRating(Application a) {
        SharedPreferences sp = a.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        int v = sp.getInt(RATING_VALUE, 0);
        long t = sp.getLong(RATING_TIME, 0);
        try {
            return Rating.newInstance(v,t);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Range getCurrentRange(Application a) {
        SharedPreferences sp = a.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        int min = sp.getInt(RANGE_MIN, 0);
        int max = sp.getInt(RANGE_MAX, 9);
        Range r =null;
        try {
            r = Range.newInstance(max,min);
        } finally {
            return r;
        }
    }

    @Override
    public void updateRating(Application a, Rating r) {
        //save in shared prefs
        SharedPreferences.Editor editor = a.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(RATING_VALUE, r.getValue());
        editor.putLong(RATING_TIME, r.getCreatedOn());
        editor.apply();

        //save in db
        if (mRatingDao==null) initDb(a);
        RatingRoomDatabase.databaseWriteExecutor.execute(()->{
            mRatingDao.insert(r);
        });
    }

    @Override
    public void updateRange(Application a, Range r) {
        SharedPreferences.Editor editor = a.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(RANGE_MAX, r.getMaxValue());
        editor.putInt(RANGE_MIN, r.getMinValue());
        editor.apply();
    }

    @Override
    public LiveData<List<Rating>> getAllRatings(Application a) {
        if (mAllRatings == null){
            initDb(a);
        }
        return mAllRatings;
    }

    private void initDb(Application a){
        RatingRoomDatabase db = RatingRoomDatabase.getDatabase(a);
        mRatingDao = db.ratingDao();
        mAllRatings = mRatingDao.getAll();
    }
}
