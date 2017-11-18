package com.footinit.selfproject.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.footinit.selfproject.ui.main.blog.BlogFragment;
import com.footinit.selfproject.ui.main.opensource.OpenSourceFragment;

/**
 * Created by Abhijit on 17-11-2017.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        tabCount = 0;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //
                return BlogFragment.newInstance();
            case 1:
                //
                return OpenSourceFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public void setCount(int tabCount) {
        this.tabCount = tabCount;
    }
}
