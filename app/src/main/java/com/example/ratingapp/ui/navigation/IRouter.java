package com.example.ratingapp.ui.navigation;

import android.content.Context;

import com.example.ratingapp.data.model.Range;
import com.example.ratingapp.data.model.Rating;

public interface IRouter {
    void handleError(Throwable throwable);
    void toHomeScreen();
    void toRateActionScreen(Rating rating, Range range);
    void toConfigureRangeScreen(Range range);
    void toRatingHistoryScreen();
}