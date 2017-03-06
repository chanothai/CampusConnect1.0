package com.company.zicure.registerkey.fragment;


import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.adapter.BannerViewPagerAdapter;
import com.company.zicure.registerkey.adapter.MainMenuAdapter;
import com.company.zicure.registerkey.holder.MainMenuHolder;
import com.company.zicure.registerkey.interfaces.ItemClickListener;
import com.company.zicure.registerkey.utilize.NextzyUtil;
import com.company.zicure.registerkey.view.viewgroup.FlyOutContainer;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "checkScroll";

    // TODO: Rename and change types of parameters
    private String checkScroll;

    //parameter
    String[] strMenu;
    int[] imgsMenu = {R.drawable.carlendar2, R.drawable.news, R.drawable.subject2, R.drawable.icon_header};

    int[] imgBanner = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};

    private int currentPager;

    //create time for run view pager
    Timer time = null;

    //create dots
    private TextView[] dots;
    //dot layout
    private LinearLayout dotLayout;

    //Adapter view pager
    private BannerViewPagerAdapter bannerViewPagerAdapter;

    //list app
    private RecyclerView recyclerViewMenu;

    //pager banner
    private ViewPager viewPagerBanner;

    private Button btnSetting;

    private NestedScrollView scrollViewMenu;

    //Swipe for pull to refresh
    PullRefreshLayout swipeRefreshLayout;

    //dialog setting
    private Dialog dialog = null;

    //view switch
    private SwitchCompat swBluetooth = null;
    private ImageView imgClose = null;

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
    public static HomeFragment newInstance(String checkScroll) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, checkScroll);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            checkScroll = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewMenu = (RecyclerView) root.findViewById(R.id.recycler_menu);
        viewPagerBanner = (ViewPager) root.findViewById(R.id.view_pager_banner);
        dotLayout = (LinearLayout)root.findViewById(R.id.layoutDot);
        btnSetting = (Button) root.findViewById(R.id.btn_setting);
        scrollViewMenu = (NestedScrollView) root.findViewById(R.id.scrollViewMenu);
        swipeRefreshLayout = (PullRefreshLayout) root.findViewById(R.id.swipeContainer);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        strMenu = new String[]{getString(R.string.menu_calendar), getString(R.string.menu_news), getString(R.string.menu_subject),getString(R.string.ePayment)};
        btnSetting.setOnClickListener(this);
        recyclerViewMenu.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        if (savedInstanceState == null){
            //set layout app
            setAdapterView();
            setBannerPager();
            createDialog();
            runScrollViewUp();
        }

        swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NextzyUtil.launchDelay(new NextzyUtil.LaunchCallback() {
                    @Override
                    public void onLaunchCallback() {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "Load Complete", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        swipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
    }

    private void createDialog(){
        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_setting_iconnect);

        swBluetooth = (SwitchCompat) dialog.findViewById(R.id.switch_bluetooth);
        imgClose = (ImageView) dialog.findViewById(R.id.img_close);
        imgClose.setOnClickListener(this);
    }


    public void setBannerPager(){
        bannerViewPagerAdapter = new BannerViewPagerAdapter(getChildFragmentManager(), imgBanner);
        viewPagerBanner.setAdapter(bannerViewPagerAdapter);
        viewPagerBanner.addOnPageChangeListener(this);

        //add dot pager
        addBottomDots(0);
    }

    public void runScrollViewUp(){
        scrollViewMenu.smoothScrollTo(0,0);
    }

    // set view adapter of student's app ----------------------------------------------->
    public void setAdapterView(){
        //set adapter
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(getActivity(), strMenu, imgsMenu) {
            //Data is bound to view
            @Override
            public void onBindViewHolder(final MainMenuHolder holder, int position) {
                holder.topicMenu.setText(strMenu[position]);
                holder.imgBtnMenu.setImageResource(imgsMenu[position]);

                holder.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (FlyOutContainer.checkMenu == 1){

                        }else{
                            String checkMenu = getItemName(position);
                            if (checkMenu.equalsIgnoreCase(getString(R.string.menu_calendar))){
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, AppMenuFragment.newInstance(getString(R.string.url_calendar), ""));
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                            else if (checkMenu.equalsIgnoreCase(getString(R.string.menu_news))){
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, AppMenuFragment.newInstance(getString(R.string.url_news), ""));
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                            else if (checkMenu.equalsIgnoreCase(getString(R.string.menu_subject))){
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, AppMenuFragment.newInstance(getString(R.string.url_subject), ""));
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                            else if (checkMenu.equalsIgnoreCase(getString(R.string.ePayment))){
//                                 Intent intent = getContext().getPackageManager().getLaunchIntentForPackage("com.company.zicure.payment");
//                                 intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                                 startActivity(intent);
                                String packageName = "com.company.zicure.payment";
                                String fullClassName = "com.company.zicure.payment.activity.AuthActivity";
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName(packageName, fullClassName));
                                startActivity(intent);
                            }
                        }
                    }
                });
            }
        };


        recyclerViewMenu.setAdapter(mainMenuAdapter);
        recyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onResume() {
        super.onResume();
        //run pager
        runViewPager();
    }

    @Override
    public void onPause() {
        super.onPause();
        time.cancel();
    }

    //<-------------------------------------------------------------------------------------------

    //****************Viewpager Listener ****************************************************
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        addBottomDots(position);
        int pager = viewPagerBanner.getCurrentItem();

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_SETTLING){

        }else{

        }
    }
    //*************************************

    //manage view pager in 1 fragment
    public Fragment getActivityFragment(ViewPager container, int position){
        String name = "android:swicher:" + container.getId() + ":" + position;
        return getActivity().getSupportFragmentManager().findFragmentByTag(name);
    }

    // Run auto change view pager ----------------------------------->
    public void runViewPager(){
        NextzyUtil.launchDelay(new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                final Handler handler = new Handler();
                final Runnable updateView = new Runnable() {
                    @Override
                    public void run() {
                        if (currentPager <= 0){
                            currentPager = 0;
                            viewPagerBanner.setCurrentItem(currentPager++);
                        }else if (currentPager == 1){
                            viewPagerBanner.setCurrentItem(currentPager++);
                        }else if (currentPager == 2){
                            viewPagerBanner.setCurrentItem(currentPager);
                            currentPager = 0;
                        }
                    }
                };

                time = new Timer();
                time.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(updateView);
                    }
                },300,5000);
            }
        });
    }

    public void stopViewPager(){
        time.cancel();
    }
    //<----------------------------------------------------------------

    private void addBottomDots(int currentPage){
        try{
            dots = new TextView[imgBanner.length];
            dotLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++){
                dots[i] = new TextView(getActivity());
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(getResources().getColor(R.color.color_dot_inactive));
                dotLayout.addView(dots[i]);
            }

            if (dots.length > 0){
                dots[currentPage].setTextColor(getResources().getColor(R.color.color_dot_active));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopViewPager();
    }

    @Override
    public void onClick(View view) {
        int tag = view.getId();
        switch (tag){
            case R.id.btn_setting:
                dialog.show();
                break;
            case R.id.img_close:
                dialog.cancel();
                break;
        }
    }
}
