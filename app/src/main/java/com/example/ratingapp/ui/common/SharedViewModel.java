package com.example.ratingapp.ui.common;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ratingapp.data.MainRepo;
import com.example.ratingapp.data.model.Range;
import com.example.ratingapp.data.model.Rating;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {

    private MutableLiveData<Rating> mRating = new MutableLiveData<>();
    private MutableLiveData<Range> mRange = new MutableLiveData<>();
    private LiveData<List<Rating>> mList = null;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        initData();
    }

    public LiveData<Rating> getRating(){
        return mRating;
    }

    public LiveData<Range> getRange(){
        return mRange;
    }

    public void updateRating(Rating rating){
        mRating.postValue(rating);
        MainRepo.INSTANCE.updateRating(getApplication(), rating);
    }

    public void updateRange(Range range){
        mRange.postValue(range);
        MainRepo.INSTANCE.updateRange(getApplication(), range);
    }

    public LiveData<List<Rating>> getAllRatings(){
        if (mList==null)
            mList = MainRepo.INSTANCE.getAllRatings(getApplication());
        return mList;
    }

    private void initData(){
        //get rating
        mRating.postValue(MainRepo.INSTANCE.getCurrentRating(getApplication()));
        //get range
        mRange.postValue(MainRepo.INSTANCE.getCurrentRange(getApplication()));
    }

}
