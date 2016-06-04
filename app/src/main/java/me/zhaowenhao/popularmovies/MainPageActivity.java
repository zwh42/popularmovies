package me.zhaowenhao.popularmovies;

import android.support.v4.app.Fragment;

/**
 * Created by zhaowenhao on 16/6/4.
 */
public class MainPageActivity extends SingleFragmentActivity {
    private static final String TAG = "MainPageActivity";

    @Override
    public Fragment createFragment(){
        return new MainPageFragment();
    }
}
