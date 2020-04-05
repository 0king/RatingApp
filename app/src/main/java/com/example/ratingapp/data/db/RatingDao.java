package com.example.ratingapp.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.ratingapp.data.model.Rating;

import java.util.List;

@Dao
public interface RatingDao {
    @Query("SELECT * FROM rating ORDER BY created_on DESC")
    LiveData<List<Rating>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Rating r);
}
