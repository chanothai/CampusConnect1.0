package com.company.zicure.campusconnect.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.company.zicure.campusconnect.fragment.BannerFragment;

import java.util.List;

import gallery.zicure.company.com.modellibrary.models.bloc.ResponseBlocUser;

/**
 * Created by Pakgon on 4/28/2017 AD.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    private List<ResponseBlocUser.ResultBlocUser.DataBloc.UserAccessControl> data = null;

    public CategoryPagerAdapter(FragmentManager manager, List<ResponseBlocUser.ResultBlocUser.DataBloc.UserAccessControl> data) {
        super(manager);
        this.data = data;
    }
    @Override
    public Fragment getItem(int position) {
        return BannerFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
