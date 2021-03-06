package com.mmga.cocode.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mmga.cocode.ui.fragment.LatestFragment;

/**
 * Created by mmga on 2015/12/20.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter{


    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return new LatestFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
