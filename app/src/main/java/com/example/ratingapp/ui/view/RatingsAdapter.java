package com.example.ratingapp.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratingapp.R;
import com.example.ratingapp.data.model.Rating;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RatingsAdapter extends RecyclerView.Adapter<RatingsAdapter.RatingHolder> {

    private List<Rating> mList;
    private SimpleDateFormat f = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.getDefault());

    RatingsAdapter(List<Rating> list){
        mList = list;
    }

    @NonNull
    @Override
    public RatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating, parent, false);
        return new RatingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingHolder holder, int position) {
        if (mList!=null) holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    protected class RatingHolder extends RecyclerView.ViewHolder{
        private TextView tvValue, tvDate;
        RatingHolder(View view){
            super(view);
            tvValue = view.findViewById(R.id.tvValue);
            tvDate = view.findViewById(R.id.tvTime);
        }
        void bind(Rating rating){
            tvValue.setText(String.valueOf(rating.getValue()));
            tvDate.setText(getFormattedDate(rating.getCreatedOn()));
        }
    }

    private String getFormattedDate(long time){
        return f.format(time);
    }
}
