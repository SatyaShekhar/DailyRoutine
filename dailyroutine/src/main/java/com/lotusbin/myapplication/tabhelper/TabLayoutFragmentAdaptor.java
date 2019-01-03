package com.lotusbin.myapplication.tabhelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lotusbin.myapplication.BlankFragment;
import com.lotusbin.myapplication.TODOListFragment;

public class TabLayoutFragmentAdaptor extends FragmentPagerAdapter {

    int numberOfTabs;

    public TabLayoutFragmentAdaptor(FragmentManager fm, int tabs) {
        super(fm);
        this.numberOfTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case  0:
                return new BlankFragment();
            case  1:
                return new TODOListFragment();
            case  2:
                return new BlankFragment();
            case  3:
                return new TODOListFragment();
            default:
                return new BlankFragment();
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
