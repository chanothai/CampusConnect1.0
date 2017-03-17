package com.company.zicure.registerkey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.company.zicure.registerkey.fragment.BannerFragment;

/**
 * Created by 4GRYZ52 on 12/1/2016.
 */

public class BannerViewPagerAdapter extends FragmentPagerAdapter {

    private int[] imgBanner = null;
    public BannerViewPagerAdapter(FragmentManager fm, int[] imgBanner) {
        super(fm);
        this.imgBanner = imgBanner;
    }

    @Override
    public Fragment getItem(int position) {

        return BannerFragment.newInstance(imgBanner[position]);
    }

    @Override
    public int getCount() {
        return imgBanner.length;
    }

}
