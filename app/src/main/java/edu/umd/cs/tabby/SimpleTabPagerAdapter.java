package edu.umd.cs.tabby;

import android.graphics.drawable.Drawable;
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
                return "Who";
            case 1:
                return "What";
            case 2:
                return "Where";
            case 3:
                return "When";
            case 4:
                return "How";
        }
        return null;
    }
}