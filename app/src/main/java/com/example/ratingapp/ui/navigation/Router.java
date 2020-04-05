package com.example.ratingapp.ui.navigation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ratingapp.R;
import com.example.ratingapp.data.model.Range;
import com.example.ratingapp.data.model.Rating;
import com.example.ratingapp.ui.view.ConfigureRangeFragment;
import com.example.ratingapp.ui.view.HomeFragment;
import com.example.ratingapp.ui.view.RateActionFragment;
import com.example.ratingapp.ui.view.RatingHistoryFragment;

public enum Router implements IRouter {
    INSTANCE;

    private FragmentManager fm;

    private static final String TAG_HOME = "TAG_HOME";
    private static final String TAG_RATE = "TAG_RATE";
    private static final String TAG_RANGE = "TAG_RANGE";
    private static final String TAG_HISTORY = "TAG_HISTORY";

    public void init(FragmentManager fm){
        this.fm = fm;
    }

    @Override
    public void handleError(Throwable throwable) {
        //pass onto activity
    }

    @Override
    public void toHomeScreen() {
        if (fm==null){
            handleError(new Exception("Initialize Router"));
            return;
        }
        Fragment fragment = fm.findFragmentByTag(TAG_HOME);
        if (fragment==null){
            fragment = new HomeFragment();
        }
        fm.beginTransaction()
                .replace(R.id.main_container, fragment, TAG_HOME)
                .commit();
    }

    @Override
    public void toRateActionScreen(Rating rating, Range range) {
        if (fm==null){
            handleError(new Exception("Initialize Router"));
            return;
        }
        Fragment fragment = fm.findFragmentByTag(TAG_RATE);
        if (fragment==null){
            fragment = RateActionFragment.newInstance(rating,range);
        }
        fm.beginTransaction()
                .replace(R.id.main_container, fragment, TAG_RATE)
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();
    }

    @Override
    public void toConfigureRangeScreen(Range range) {
        if (fm==null){
            handleError(new Exception("Initialize Router"));
            return;
        }
        Fragment fragment = fm.findFragmentByTag(TAG_RANGE);
        if (fragment==null){
            fragment = ConfigureRangeFragment.newInstance(range);
        }
        fm.beginTransaction()
                .replace(R.id.main_container, fragment, TAG_RANGE)
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();
    }

    @Override
    public void toRatingHistoryScreen() {
        if (fm==null){
            handleError(new Exception("Initialize Router"));
            return;
        }
        Fragment fragment = fm.findFragmentByTag(TAG_HISTORY);
        if (fragment==null){
            fragment = new RatingHistoryFragment();
        }
        fm.beginTransaction()
                .replace(R.id.main_container, fragment, TAG_HISTORY)
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();
    }
}
