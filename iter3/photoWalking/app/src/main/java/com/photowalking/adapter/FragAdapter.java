package com.photowalking.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lionel on 2017/8/23.
 */

public class FragAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments = new ArrayList<>();

    public FragAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addItem(Fragment frag){
        fragments.add(frag);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


}
