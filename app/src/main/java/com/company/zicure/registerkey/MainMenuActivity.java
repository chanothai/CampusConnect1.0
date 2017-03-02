package com.company.zicure.registerkey;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.registerkey.adapter.SlideMenuAdapter;
import com.company.zicure.registerkey.common.BaseActivity;
import com.company.zicure.registerkey.fragment.AppMenuFragment;
import com.company.zicure.registerkey.fragment.HomeFragment;
import com.company.zicure.registerkey.fragment.ScanQRFragment;
import com.company.zicure.registerkey.fragment.StudentCardFragment;
import com.company.zicure.registerkey.holder.SlideMenuHolder;
import com.company.zicure.registerkey.interfaces.ItemClickListener;
import com.company.zicure.registerkey.models.drawer.SlideMenuDetail;
import com.company.zicure.registerkey.network.ChromeService;
import com.company.zicure.registerkey.utilize.EventBusCart;
import com.company.zicure.registerkey.utilize.ModelCart;
import com.company.zicure.registerkey.utilize.NextzyUtil;
import com.company.zicure.registerkey.view.viewgroup.FlyOutContainer;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;

public class MainMenuActivity extends BaseActivity implements OnLocationUpdatedListener,OnTabSelectListener,OnTabReselectListener {

    //layout coordinator cover material layout
    @Bind(R.id.rootLayout)
    CoordinatorLayout coordinatorLayout;

    //toolbar
    @Bind(R.id.toolbar)
    Toolbar toolbarMenu;
    @Bind(R.id.title_toolbar)
    TextView titleToolbar;
    @Bind(R.id.img_toggle)
    ImageView imgToggle;

    //bottom navigation
    @Bind(R.id.bottomBar)
    BottomBar bottomBar;


    //list view slide menu
    @Bind(R.id.list_slide_menu)
    RecyclerView listSlideMenu;

    //img_close in slide menu
    @Bind(R.id.img_close)
    ImageView imgCloseMenu;

    //list slide menu
    ArrayList<SlideMenuDetail> arrMenu = null;

    //view layout
    private FlyOutContainer root;

    //fragment layout
    private HomeFragment homeFragment = null;
    private ScanQRFragment scanQRFragment = null;
    private StudentCardFragment studentCardFragment = null;

    private Uri uri = null;
    private ChromeService chromeService = null;

    private static final int PORT = 5055;
    private static final String DEVICEID = "218989";


    //data test
    int[] imgsIconMenu = {R.drawable.ic_action_home, R.drawable.ic_2,R.drawable.ic_3,R.drawable.ic_4, R.drawable.ic_5, R.drawable.ic_6,
    R.drawable.ic_7, R.drawable.ic_8, R.drawable.ic_9};

    int[] titleMenu = {R.string.slide_menu_home, R.string.slide_menu_student, R.string.slide_menu_learning,
            R.string.slide_menu_exam, R.string.slide_menu_calender, R.string.slide_menu_transcript,
            R.string.slide_menu_activity, R.string.slide_menu_due, R.string.slide_menu_edit};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = (FlyOutContainer) getLayoutInflater().inflate(R.layout.activity_main_menu, null);
        setContentView(root);
        ButterKnife.bind(this);
        EventBusCart.getInstance().getEventBus().register(this);

        if (savedInstanceState == null){
            setToolbar();
            bottomBar.setOnTabSelectListener(this);
            bottomBar.setOnTabReselectListener(this);
            //set slide menu
            listSlideMenu.setLayoutManager(new LinearLayoutManager(this));
            setSlideMenuAdapter();
        }

        imgCloseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToggle();
            }
        });
    }

    public void setToolbar(){
        toolbarMenu.setTitle("");
        titleToolbar.setText(getString(R.string.campus_logo));
        imgToggle.setImageResource(R.drawable.ic_action_toc);
        imgToggle.setColorFilter(ContextCompat.getColor(this, R.color.color_text));
        imgToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToggle();
            }
        });
        setSupportActionBar(toolbarMenu);
    }

    public void setToggle(){
        root.toggleMenu();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    @Override
    public void onBackPressed() {
//        openActivity(LoginActivity.class, true);
//        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        int count = getFragmentManager().getBackStackEntryCount();
        if (count > 0){
        }
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        getLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        SmartLocation.with(this).location().stop();
    }

    public HomeFragment setHomeFragment(){
        FragmentManager fm = getSupportFragmentManager();
        HomeFragment homeFragment = (HomeFragment) fm.findFragmentByTag(getString(R.string.tag_home_fragment));
        return homeFragment;
    }

    public void getLocation(){
        try{
            if (SmartLocation.with(this).location().state().isNetworkAvailable()){
                if(SmartLocation.with(this).location().state().locationServicesEnabled()) {
                    if (SmartLocation.with(this).location().state().isGpsAvailable()){
                        LocationParams params = new LocationParams.Builder()
                                .setAccuracy(LocationAccuracy.HIGH)
                                .setInterval(10000)
                                .build();

                        SmartLocation.with(this)
                                .location(new LocationGooglePlayServicesWithFallbackProvider(this))
                                .config(params)
                                .start(this);
                    }
                }else{ //if not denied
                    //Intent to setting location service
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                }
            }else{
                startActivityForResult(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS), 1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Location Listener ***************************************************************************
    @Override
    public void onLocationUpdated(Location location) {
        try{
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            ModelCart.getInstance().getModel().locationModel.latLocate = latitude;
            ModelCart.getInstance().getModel().locationModel.longLocate = longitude;

            float accuracy = location.getAccuracy();
            float bearing = location.getBearing();
            String provider = location.getProvider();

        }catch (Exception e){
            e.printStackTrace();
        }
        Position position = new Position(DEVICEID, location, getBatteryLevel());
        send(position);
    }

    //******************************************************

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private double getBatteryLevel() {
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR) {
            Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);
            return (level * 100.0) / scale;
        } else {
            return 0;
        }
    }

    private void send(final Position position) {
        String request = ProtocolFormatter.formatRequest(getString(R.string.url_map_service), PORT, false, position);
        RequestManager.sendRequestAsync(request, new RequestManager.RequestHandler() {
            @Override
            public void onComplete(boolean success) {
                if (success) {
                    Log.d("Position", "send success");
                } else {
                    Log.d("Position", "send fails");
                }
            }
        });
    }

    //Bottom navigation listener *******************************************
    @Override
    public void onTabSelected(@IdRes int tabId) {
        if (tabId == R.id.tab_home){
            homeFragment = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, homeFragment,getString(R.string.tag_home_fragment));
            transaction.commit();
        }

        else if (tabId == R.id.tab_card){
            studentCardFragment = new StudentCardFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, studentCardFragment);
            transaction.commit();
        }

        else if (tabId == R.id.tab_scan){
            scanQRFragment = new ScanQRFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, scanQRFragment);
            transaction.commit();
        }
    }

    @Override
    public void onTabReSelected(@IdRes int tabId) {
        if (tabId == R.id.tab_home){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, HomeFragment.newInstance(""), getString(R.string.tag_home_fragment));
            transaction.commit();

            int count = getFragmentManager().getBackStackEntryCount();
            if (count > 0){
                this.onBackPressed();
            }
        }
        if (tabId == R.id.tab_card){
            Toast.makeText(getApplicationContext(), "TAB: "+ "Student card", Toast.LENGTH_SHORT).show();
        }

        if (tabId == R.id.tab_scan){
            Toast.makeText(getApplicationContext(), "TAB: "+ "Scan QR code", Toast.LENGTH_SHORT).show();
        }
    }

    // set view adapter of slide menu0--------------------------------------->
    public void setSlideMenuAdapter(){
        arrMenu = new ArrayList<SlideMenuDetail>();

        for (int i = 0; i < imgsIconMenu.length; i++){
            SlideMenuDetail menu = new SlideMenuDetail();
            menu.setImage(imgsIconMenu[i]);
            menu.setTitle(getString(titleMenu[i]));
            arrMenu.add(menu);
        }

        Gson gson = new Gson();
        String strJson = gson.toJson(arrMenu);
        Log.d("SlideMenu", strJson);

        SlideMenuAdapter slideMenuAdapter = new SlideMenuAdapter(this, arrMenu) {
            @Override
            public void onBindViewHolder(SlideMenuHolder holder, int position) {
                if (position == 0){
                    holder.imgIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.color_text));
                }
                final String title = arrMenu.get(position).getTitle();
                holder.subTitle.setText(title);
                holder.imgIcon.setImageResource(arrMenu.get(position).getImage());

                holder.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (title.equalsIgnoreCase(getString(R.string.slide_menu_home))){
                            bottomBar.selectTabAtPosition(0);
                            setToggle();
                        }
                        else if (title.equalsIgnoreCase(getString(R.string.slide_menu_student))){
                            bottomBar.selectTabAtPosition(2);
                            setToggle();
                        }

                        else if (title.equalsIgnoreCase(getString(R.string.slide_menu_learning))){
                            setToggle();
                            getAppCalendar();
                        }

                        else if (title.equalsIgnoreCase(getString(R.string.slide_menu_exam))){
                            setToggle();
                            getAppCalendar();
                        }
                        else if (title.equalsIgnoreCase(getString(R.string.slide_menu_calender))){
                            setToggle();
                            getAppCalendar();
                        }
                        else if (title.equalsIgnoreCase(getString(R.string.slide_menu_transcript))){
                            setToggle();
                            getAppCalendar();
                        }
                        else if (title.equalsIgnoreCase(getString(R.string.slide_menu_due))){
                            setToggle();
                        }
                        else if (title.equalsIgnoreCase(getString(R.string.slide_menu_edit))){
                            setToggle();
                        }
                    }
                });
            }
        };
        listSlideMenu.setAdapter(slideMenuAdapter);
        listSlideMenu.setItemAnimator(new DefaultItemAnimator());
//        listSlideMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    //<=======================================================================

    public void getAppCalendar(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, AppMenuFragment.newInstance(getString(R.string.url_calendar), ""));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
