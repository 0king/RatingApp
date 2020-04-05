package com.example.ratingapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ratingapp.data.model.Range;
import com.example.ratingapp.data.model.Rating;

import java.util.List;

public interface IRepo {
    Rating getCurrentRating(Application a);
    Range getCurrentRange(Application a);
    void updateRating(Application a, Rating r);
    void updateRange(Application a, Range r);
    LiveData<List<Rating>> getAllRatings(Application a);
}
