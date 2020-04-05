package com.example.ratingapp.ui.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.ratingapp.R;
import com.example.ratingapp.data.model.Range;
import com.example.ratingapp.ui.common.SharedViewModel;

public class ConfigureRangeFragment extends Fragment {

    private static final String ARG_RANGE = "RANGE";

    private Range mRange;
    private SharedViewModel mViewModel;

    private SeekBar mSeekBarMin, mSeekBarMax;

    public ConfigureRangeFragment() {
        // Required empty public constructor
    }

    public static ConfigureRangeFragment newInstance(Range range) {
        ConfigureRangeFragment fragment = new ConfigureRangeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RANGE, range);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRange = getArguments().getParcelable(ARG_RANGE);
        }
        mViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configure_range, container, false);
        mSeekBarMin = view.findViewById(R.id.seekbar_min);
        mSeekBarMax = view.findViewById(R.id.seekbar_max);
        mSeekBarMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onSeekBarMinChange(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        mSeekBarMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onSeekBarMaxChange(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        observeData();
        return view;
    }

    private void onSeekBarMinChange(int progress){
        int max = mViewModel.getRange().getValue().getMaxValue();
        if (progress>max){
            showToast("min cannot be > max");
            mSeekBarMin.setProgress(0);
        }
        else {
            try {
                mViewModel.updateRange(Range.newInstance(max,progress));
            }catch (Exception e){
                //do nothing
            }
        }
    }

    private void onSeekBarMaxChange(int progress){
        int min = mViewModel.getRange().getValue().getMinValue();
        if (progress<min){
            showToast("max cannot be < min");
            mSeekBarMax.setProgress(9);
        }
        else {
            try {
                mViewModel.updateRange(Range.newInstance(progress,min));
            }catch (Exception e){
                //do nothing
            }
        }
    }

    private void observeData(){
        mViewModel.getRange().observe(getViewLifecycleOwner(), range -> {
            mSeekBarMin.setProgress(range.getMinValue());
            mSeekBarMax.setProgress(range.getMaxValue());
        });
    }

    private void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
