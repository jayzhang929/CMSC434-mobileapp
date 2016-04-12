package edu.umd.cs.tabby;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class SimpleTabPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 5;

    public SimpleTabPagerAdapter(FragmentManager fm) {

        super(fm);

    }


    @Override
    public Fragment getItem(int position) {

        return SimpleTabFragment.newInstance(position + 1);
    }

    @Override
    // HOW MANY TABS TO CREATE
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "tab1";
            case 1:
                return "tab2";
            case 2:
                return "tab3";
            case 3:
                return "tab4";
            case 4:
                return "tab5";
        }
        return null;
    }
}