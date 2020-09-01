package com.hjk.music_3.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hjk.music_3.ui.fragment.DreamFragment;
import com.hjk.music_3.ui.fragment.MusicFragment;
import com.hjk.music_3.ui.fragment.ProfileFragment;
import com.hjk.music_3.ui.fragment.ScapeFragment;
import com.hjk.music_3.ui.fragment.SleepFragment;


public class MyPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs; //tab의 갯수

    public MyPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DreamFragment tab1 = DreamFragment.newInstance();
                return tab1;
            case 1:
                SleepFragment tab2 = SleepFragment.newInstance();
                return tab2;
            case 2:
                ScapeFragment tab3 = ScapeFragment.newInstance();
                return tab3;
            default:
                return null;
        }
        //return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}