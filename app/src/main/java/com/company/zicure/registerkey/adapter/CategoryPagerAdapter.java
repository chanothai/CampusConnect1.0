package com.company.zicure.registerkey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.company.zicure.registerkey.fragment.BannerFragment;

import java.util.List;

import gallery.zicure.company.com.modellibrary.models.CategoryModel.Result.Data;

/**
 * Created by Pakgon on 4/28/2017 AD.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    private List<Data> data = null;

    public CategoryPagerAdapter(FragmentManager manager, List<Data> data) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getBlogCategories().getBlocCategoryName();
    }
}
