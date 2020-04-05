package com.example.ratingapp.ui.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ratingapp.R;
import com.example.ratingapp.data.model.Range;
import com.example.ratingapp.data.model.Rating;
import com.example.ratingapp.ui.navigation.Router;
import com.example.ratingapp.ui.common.SharedViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RateActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RateActionFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RANGE = "range";
    private static final String ARG_RATING = "rating";

    private Range mRange;
    private Rating mRating;

    private SharedViewModel mViewModel;

    //views
    private SeekBar mSeekBar;
    private Button mConfigBtn, mSubmitBtn;
    private TextView mMinView, mMaxView;


    public RateActionFragment() {
        // Required empty public constructor
    }

    public static RateActionFragment newInstance(Rating rating, Range range) {
        RateActionFragment fragment = new RateActionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RANGE, range);
        args.putParcelable(ARG_RATING, rating);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRange = getArguments().getParcelable(ARG_RANGE);
            mRating = getArguments().getParcelable(ARG_RATING);
        }
        mViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_action, container, false);
        initUi(view);
        observeData();
        return view;
    }

    private void initUi(View view){
        mSeekBar = view.findViewById(R.id.seekbar);
        mSubmitBtn = view.findViewById(R.id.btnSubmit);
        mConfigBtn = view.findViewById(R.id.btnConfig);
        mMinView = view.findViewById(R.id.tvMinValue);
        mMaxView = view.findViewById(R.id.tvMaxValue);;
        mSubmitBtn.setOnClickListener(this);
        mConfigBtn.setOnClickListener(this);
    }

    private void observeData(){
        mViewModel.getRange().observe(getViewLifecycleOwner(), range -> {
            updateViewOnRangeChange(range);
        });
        /*mViewModel.getRating().observe(getViewLifecycleOwner(), rating -> {
            mSeekBar.setProgress(rating.getValue());
        });*/
    }

    private void updateViewOnRangeChange(Range range){
        int min = range.getMinValue();
        mSeekBar.setMin(min);
        mMinView.setText(String.valueOf(min));

        int max = range.getMaxValue();
        mSeekBar.setMax(max);
        mMaxView.setText(String.valueOf(max));

        mSeekBar.setProgress(min);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConfig:
                Router.INSTANCE.toConfigureRangeScreen(mViewModel.getRange().getValue());
                break;
            case R.id.btnSubmit:
                createNewRating();
                resetSeekBar();
                break;
        }
    }

    //create new rating and update view
    private void createNewRating(){
        try {
            int val = mSeekBar.getProgress();
            Rating r = Rating.newInstance(val, System.currentTimeMillis());
            mViewModel.updateRating(r);
            showToast("Rating = " + val + " created");
        } catch (Exception e) {
            showToast("Invalid value");
        }
    }

    private void resetSeekBar(){
        Range r = mViewModel.getRange().getValue();
        mSeekBar.setProgress(r==null?0:r.getMinValue());
    }

    private void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
