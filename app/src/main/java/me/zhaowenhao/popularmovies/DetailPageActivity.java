package me.zhaowenhao.popularmovies;

import android.support.v4.app.Fragment;

/**
 * Created by zhaowenhao on 16/6/7.
 */
public class DetailPageActivity extends SingleFragmentActivity {
    private static final String TAG = "DetailPageActivity";

    @Override
    public Fragment createFragment(){
        return new DetailPageFragment();
    }
}
