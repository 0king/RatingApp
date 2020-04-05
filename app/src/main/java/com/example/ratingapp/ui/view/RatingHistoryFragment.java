package com.example.ratingapp.ui.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ratingapp.R;
import com.example.ratingapp.ui.common.SharedViewModel;

public class RatingHistoryFragment extends Fragment {

    private SharedViewModel mViewModel;

    private RecyclerView mRatingListView;
    //private RatingsAdapter mAdapter;
    private ProgressBar mProgressBar;

    public RatingHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_history, container, false);
        initUi(view);
        observeData();
        return view;
    }

    private void initUi(View v){
        mProgressBar = v.findViewById(R.id.progressBar);
        mRatingListView = v.findViewById(R.id.recycler_view);
        Context context = v.getContext();
        mRatingListView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        mRatingListView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        mRatingListView.setHasFixedSize(true);
    }

    private void observeData(){
        mViewModel.getAllRatings().observe(getViewLifecycleOwner(), ratings -> {
            mRatingListView.setAdapter(new RatingsAdapter(ratings));
            onFinishLoading();
        });
    }

    public void onStartLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void onFinishLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

}
