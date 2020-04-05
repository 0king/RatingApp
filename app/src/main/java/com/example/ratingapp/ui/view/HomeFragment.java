package com.example.ratingapp.ui.view;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ratingapp.R;
import com.example.ratingapp.ui.navigation.Router;
import com.example.ratingapp.ui.common.SharedViewModel;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button mRatingBtn;
    private Button mHistoryBtn;
    private SharedViewModel mViewModel;
    private String mRating, mRange;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initUi(view);
        observeData();
        return view;
    }

    private void initUi(View view){
        mRatingBtn = view.findViewById(R.id.btnRating);
        mHistoryBtn = view.findViewById(R.id.btnHistory);
        mRatingBtn.setOnClickListener(this);
        mHistoryBtn.setOnClickListener(this);
    }

    private void observeData(){
        mViewModel.getRating().observe(getViewLifecycleOwner(), rating -> {
            mRating = "Rating = " + rating.toString();
            updateView();
        });
        mViewModel.getRange().observe(getViewLifecycleOwner(), range -> {
            mRange = range.toString();
            updateView();
        });
    }

    private void updateView(){
        mRatingBtn.setText(mRating + " " + mRange);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRating:
                Router.INSTANCE.toRateActionScreen(mViewModel.getRating().getValue(),
                        mViewModel.getRange().getValue());
                break;
            case R.id.btnHistory:
                Router.INSTANCE.toRatingHistoryScreen();
                break;
        }
    }
}
