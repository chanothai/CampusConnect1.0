package com.company.zicure.registerkey.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.adapter.CategoryPagerAdapter;

import java.util.List;

import gallery.zicure.company.com.modellibrary.utilize.ModelCart;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // TODO: Rename and change types of parameters
    private ViewPager categoryPager = null;
    private TabLayout scrollTabs = null;

    private String[] bannerImg = null;
    private List<String> arrTitle = null;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        categoryPager = (ViewPager) root.findViewById(R.id.pager_topic);
        scrollTabs = (TabLayout) root.findViewById(R.id.scroll_tabs);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){

            setupViewPager();
        }
    }

    private void setupViewPager(){
        CategoryPagerAdapter adapter = new CategoryPagerAdapter(getChildFragmentManager(), ModelCart.getInstance().getCategoryModel().getResult().getData());
        categoryPager.setAdapter(adapter);

        scrollTabs.setupWithViewPager(categoryPager);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }
}
