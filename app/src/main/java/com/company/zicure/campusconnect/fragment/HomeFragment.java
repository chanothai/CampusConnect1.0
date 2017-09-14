package com.company.zicure.campusconnect.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.adapter.CategoryPagerAdapter;
import com.company.zicure.campusconnect.adapter.MainMenuAdapter;
import com.company.zicure.campusconnect.contents.ContentAdapterCart;

import java.util.List;

import gallery.zicure.company.com.modellibrary.models.bloc.ResponseBlocUser;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener ,ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // TODO: Rename and change types of parameters
    private ViewPager categoryPager = null;

    //Swipe for pull to refresh
    private SwipeRefreshLayout swipeRefreshLayout;

    //list app
    private RecyclerView recyclerViewMenu;

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
    public static HomeFragment newInstance(int pager) {
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
        recyclerViewMenu = (RecyclerView) root.findViewById(R.id.recycler_menu);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainer);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        categoryPager.addOnPageChangeListener(this);

        if (savedInstanceState == null){
            setupViewPager();
            setSwipeRefreshLayout();
            setAdapterView(0);
        }
    }

    private List<ResponseBlocUser.ResultBlocUser.DataBloc.UserAccessControl> getCategoryData(){
        return ModelCart.getInstance().getUserBloc().getResult().getData().getBloc();
    }

    private void setSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
    }

    private void setupViewPager(){
        CategoryPagerAdapter adapter = new CategoryPagerAdapter(getChildFragmentManager(), ModelCart.getInstance().getUserBloc().getResult().getData().getBloc());
        categoryPager.setAdapter(adapter);
    }

    public void setAdapterView(int pager){
        if (getCategoryData().size() > 0){
            //set adapter
            MainMenuAdapter mainMenuAdapter = new ContentAdapterCart().setMainMenuAdapter(getActivity(), getCategoryData().get(pager).getBlocs(), pager);

            recyclerViewMenu.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerViewMenu.setAdapter(mainMenuAdapter);
            recyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Pager", Integer.toString(position));
        setAdapterView(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRefresh() {
//        ModelCart.getInstance().instanceCategory();
//        ClientHttp.getInstance(getActivity()).requestUserBloc(ModelCart.getInstance().getAuth().getAuthToken());
        swipeRefreshLayout.setRefreshing(false);
    }
}
