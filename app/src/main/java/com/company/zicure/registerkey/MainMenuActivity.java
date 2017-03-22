package com.company.zicure.registerkey;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.registerkey.adapter.SlideMenuAdapter;
import com.company.zicure.registerkey.common.BaseActivity;
import com.company.zicure.registerkey.fragment.AppMenuFragment;
import com.company.zicure.registerkey.fragment.HomeFragment;
import com.company.zicure.registerkey.fragment.ScanQRFragment;
import com.company.zicure.registerkey.fragment.StudentCardFragment;
import com.company.zicure.registerkey.holder.SlideMenuHolder;
import com.company.zicure.registerkey.interfaces.ItemClickListener;
import com.company.zicure.registerkey.models.ApplicationRequest;
import com.company.zicure.registerkey.models.BaseResponse;
import com.company.zicure.registerkey.models.DataModel;
import com.company.zicure.registerkey.models.ResponseUserInfo;
import com.company.zicure.registerkey.models.drawer.SlideMenuDetail;
import com.company.zicure.registerkey.models.AuthToken;
import com.company.zicure.registerkey.network.ClientHttp;
import com.company.zicure.registerkey.security.EncryptionAES;
import com.company.zicure.registerkey.utilize.EventBusCart;
import com.company.zicure.registerkey.utilize.ModelCart;
import com.company.zicure.registerkey.utilize.ResizeScreen;
import com.company.zicure.registerkey.utilize.RestoreLogin;
import com.company.zicure.registerkey.view.viewgroup.FlyOutContainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joooonho.SelectableRoundedImageView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import profilemof.zicure.company.com.profilemof.activity.ProfileActivity;

public class MainMenuActivity extends BaseActivity implements OnTabSelectListener,OnTabReselectListener, View.OnClickListener {

    //layout coordinator cover material layout
    @Bind(R.id.liner_content)
    RelativeLayout linearLayout;
    @Bind(R.id.layout_menu)
    RelativeLayout layoutMenu;
    @Bind(R.id.rootLayout)
    CoordinatorLayout coordinatorLayout;

    //toolbar
    @Bind(R.id.appbar_layout)
    AppBarLayout appBarLayout;
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
    @Bind(R.id.layout_ghost)
    FrameLayout layoutGhost;
    @Bind(R.id.control_slide)
    FrameLayout controlSlide;
    @Bind(R.id.img_profile)
    SelectableRoundedImageView imgProfile;

    //list slide menu
    private ArrayList<SlideMenuDetail> arrMenu = null;
    @Bind(R.id.child_header_drawer)
    RelativeLayout childHeaderDrawer;
    @Bind(R.id.header_drawer)
    RelativeLayout headerDrawer;

    //view layout
    private FlyOutContainer root;

    //fragment layout
    private HomeFragment homeFragment = null;
    private ScanQRFragment scanQRFragment = null;
    private StudentCardFragment studentCardFragment = null;

    private static final int PORT = 5055;
    private static final String DEVICEID = "218989";

    private String currentToken = null;
    private String currentDynamicKey = null;
    private String currentUsername = null;

    private int widthScreenMenu;
    private int haftScreen = 0;
    private VelocityTracker velocityTracker = null; // get speed for touch

    private byte[] key = null;
    private DataModel model = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = (FlyOutContainer) getLayoutInflater().inflate(R.layout.activity_main_menu, null);
        setContentView(root);

        EventBusCart.getInstance().getEventBus().register(this);
        ButterKnife.bind(this);

        bottomBar.setOnTabSelectListener(this);
        bottomBar.setOnTabReselectListener(this);

        if (savedInstanceState == null){
            checkLogin();
        }
        setOnTouchView();
    }

    private void checkLogin(){
        currentToken = RestoreLogin.getInstance(this).getRestoreToken();
        currentDynamicKey = RestoreLogin.getInstance(this).getRestoreKey();
        currentUsername = RestoreLogin.getInstance(this).getRestoreUser();

        String verifyPhone = RestoreLogin.getInstance(this).getRestorePhoneNumber();
        if (verifyPhone != null){
            if (currentDynamicKey != null && currentToken != null && currentUsername != null){
                setToolbar();
                setSlideMenuAdapter();
                key = Base64.decode(currentDynamicKey.getBytes(), Base64.NO_WRAP);
                setModelUser();
            }else{
                openActivity(LoginActivity.class, true);
            }
        }else{
            openActivity(RegisterActivity.class, true);
        }
    }
    private void setModelUser(){
        try{
            ApplicationRequest request = new ApplicationRequest();
            ApplicationRequest.Application application = new ApplicationRequest.Application();
            application.setClientID("abcdef");
            application.setSecret("123456");

            ApplicationRequest.User user = new ApplicationRequest.User();
            user.setToken(currentToken);

            request.setApplication(application);
            request.setUser(user);

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String encrypt = EncryptionAES.newInstance(key).encrypt(gson.toJson(request));

            if (encrypt != null){
                model = new DataModel();
                model.setData(encrypt);

                DataModel.User modelUser = new DataModel.User();
                modelUser.setUsername(currentUsername);

                model.setUser(modelUser);

                getUserInfo();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    public void getUserInfo(){
        if (model != null){
            showLoadingDialog();
            ClientHttp.getInstance(this).requestAuthToken(model);
        }
    }


    @Subscribe
    public void onEventAuthToken(BaseResponse baseResponse){
        if (baseResponse.getResult().getSuccess().equalsIgnoreCase("OK")){
            String[] splitKey = baseResponse.getResult().geteResult().split(getString(R.string.key_iv));
            String decrypt = EncryptionAES.newInstance(key).decrypt(splitKey[0], splitKey[1].getBytes());
            decodeJson(decrypt);
        }else{
            Toast.makeText(this, baseResponse.getResult().getError(), Toast.LENGTH_SHORT).show();
        }
        dismissDialog();
    }

    private void decodeJson(String decrypt){
        try{
            JSONObject jsonObject = new JSONObject(decrypt);
            String success = jsonObject.getString("Success");
            if (success.equalsIgnoreCase("OK")){
                jsonObject = jsonObject.getJSONObject("AuthToken");

                ModelCart.getInstance().getAuthToken().setAuthToken(jsonObject.getString("auth_token"));
                ModelCart.getInstance().getAuthToken().setExpiryDate(jsonObject.getString("auth_token_expiry_date"));

                validateCurrentDate(ModelCart.getInstance().getAuthToken());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void validateCurrentDate(AuthToken authToken){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();

            double expireDate = setSplitDate(authToken.getExpiryDate());
            double currentDate = setSplitDate(dateFormat.format(date));

            if (currentDate > expireDate){

            }else{
                String path = authToken.getAuthToken();
                ClientHttp.getInstance(this).requestUserInfo(path);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Double setSplitDate(String dateTime){
        String[] splitDate = dateTime.split(" ");
        String[] splitYear = splitDate[0].split("-");
        String[] splitTime = splitDate[1].split(":");

        String currentDate = splitYear[0] + splitYear[1] + splitYear[2] + splitTime[0]+ splitTime[1] + splitTime[2];
        return Double.parseDouble(currentDate);
    }

    @Subscribe
    public void onEventGetUserInfo(ResponseUserInfo response){
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            Log.d("UserInfo", new Gson().toJson(response.getResult()));
            ModelCart.getInstance().getUserInfo().setResult(response.getResult());
            Toast.makeText(this, response.getResult().getSuccess(), Toast.LENGTH_LONG).show();

            String pathImg = ModelCart.getInstance().getUserInfo().getResult().getData().getUser().getImgPath();
            if (pathImg != null){
                Glide.with(this)
                        .load(pathImg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(imgProfile);
            }
        }else{
            Toast.makeText(this, response.getResult().getError(), Toast.LENGTH_LONG).show();
        }
        dismissDialog();
    }

    public void setLayoutHeadDrawer(){
        ResizeScreen resizeScreen = new ResizeScreen(this);

        RelativeLayout.LayoutParams paramsIMG = (RelativeLayout.LayoutParams) imgProfile.getLayoutParams();
        paramsIMG.height = resizeScreen.widthScreen(5);
        paramsIMG.width = resizeScreen.widthScreen(5);
        imgProfile.setLayoutParams(paramsIMG);

        RelativeLayout.LayoutParams paramHeader = (RelativeLayout.LayoutParams) headerDrawer.getLayoutParams();
        paramHeader.height = resizeScreen.widthScreen(2);
        paramHeader.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        headerDrawer.setLayoutParams(paramHeader);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) childHeaderDrawer.getLayoutParams();
        params.topMargin = getStatusBarHeight() + convertPxtoDp(8);
        childHeaderDrawer.setLayoutParams(params);

        RelativeLayout.LayoutParams paramHead = (RelativeLayout.LayoutParams) headerDrawer.getLayoutParams();
        paramHead.bottomMargin = getStatusBarHeight();
        headerDrawer.setLayoutParams(paramHead);

        RelativeLayout.LayoutParams paramsMenu = (RelativeLayout.LayoutParams) listSlideMenu.getLayoutParams();
        paramsMenu.topMargin = getStatusBarHeight() * -1;
        listSlideMenu.setLayoutParams(paramsMenu);
    }

    private int convertPxtoDp(int value){
        Resources resources = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,resources.getDisplayMetrics());
        return (int)px;
    }

    public void setToolbar(){
        if (Build.VERSION.SDK_INT >= 21){
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
            params.height = getActionBarHeight() + getStatusBarHeight() + 10;
            appBarLayout.setLayoutParams(params);
            toolbarMenu.setPadding(0, getStatusBarHeight() + 10 , 0,0);

            toolbarMenu.setTitle("");
            titleToolbar.setText(getString(R.string.campus_logo));
            imgToggle.setImageResource(R.drawable.ic_action_toc);
            imgToggle.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent));
            imgToggle.setOnClickListener(this);
            setSupportActionBar(toolbarMenu);

            setLayoutHeadDrawer();
        }else{
            appBarLayout.setVisibility(View.GONE);
        }
    }

    private int getActionBarHeight(){
        int actionBarHeight = 0;
        final TypedArray styleAttributes = getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        actionBarHeight = (int) styleAttributes.getDimension(0,0);
        styleAttributes.recycle();

        return actionBarHeight;
    }

    private int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setOnTouchView(){
        controlSlide.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                view.setEnabled(true);
                drag(event, linearLayout);
                return false;
            }
        });

        layoutGhost.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                drag(motionEvent, linearLayout);
                return false;
            }
        });
    }

    private void setMotionEventUp(int margin, double speedTouch){
        int marginIn = 20;
        margin -= marginIn;
        if (margin >= widthScreenMenu){
            setToggle(widthScreenMenu - marginIn, speedTouch);
        }else{
            setToggle(margin, speedTouch);
        }
        layoutGhost.setEnabled(true);
    }

    private void setMotionEventMove(int margin, View v){
        int marginIn = 20;
        margin -= marginIn;

        if (margin >= (widthScreenMenu - marginIn)){
            v.setTranslationX(widthScreenMenu - marginIn);

            layoutGhost.setEnabled(true);

        }else {
            v.setTranslationX(margin);
            if (FlyOutContainer.menuCurrentState == FlyOutContainer.MenuState.CLOSED){
                layoutGhost.setEnabled(false);
            }
        }

        setMarginLayout(20);
        root.setAlphaMenu(margin);
        layoutGhost.setVisibility(View.VISIBLE);
    }

    private void drag(final MotionEvent event, final View v){
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        int margin = 0;
        int marginEnd = 150;
        widthScreenMenu = layoutMenu.getRootView().getWidth() - marginEnd;
        haftScreen = (widthScreenMenu - marginEnd) / 2;
        switch(action){
            case MotionEvent.ACTION_MOVE: {
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);
                margin = (int) event.getRawX();
                setMotionEventMove(margin, v);
                break;
            }
            case MotionEvent.ACTION_UP: {
                margin = (int) event.getRawX();
                Log.d("velocity", "X velocity: " + VelocityTrackerCompat.getXVelocity(velocityTracker, pointerId));
                double speedTouch = VelocityTrackerCompat.getXVelocity(velocityTracker, pointerId);
                setMotionEventUp(margin, speedTouch);
                break;
            }
            case MotionEvent.ACTION_DOWN:{
                if (velocityTracker == null){
                    velocityTracker = velocityTracker.obtain();
                }else{
                    velocityTracker.clear();
                }
                velocityTracker.addMovement(event);

                Log.d("MoveDown", "down");
                break;
            }
            case MotionEvent.ACTION_CANCEL:{
                velocityTracker.recycle();
                break;
            }
        }
    }

    public void setToggle(int widthScreen, double speedTouch){
        root.toggleMenu(widthScreen, speedTouch);
    }

    private void setMarginLayout(int left){
        //hide shadow
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) coordinatorLayout.getLayoutParams();
        params.leftMargin = left;
        coordinatorLayout.setLayoutParams(params);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count > 0){
        }
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    //Bottom navigation listener *******************************************
    @Override
    public void onTabSelected(@IdRes int tabId) {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0){
            count--;
        }
        try{
            if (tabId == R.id.tab_home) {
                homeFragment = new HomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, homeFragment, getString(R.string.tag_home_fragment));
                transaction.commit();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onTabReSelected(@IdRes int tabId) {

    }

    // set view adapter of slide menu0--------------------------------------->
    public void setSlideMenuAdapter(){
        arrMenu = new ArrayList<SlideMenuDetail>();

        Gson gson = new Gson();
        String strJson = gson.toJson(arrMenu);
        Log.d("SlideMenu", strJson);

        String[] arrTitle = {getString(R.string.menu_feed_th),getString(R.string.user_detail_th),getString(R.string.edit_user_th),getString(R.string.setting_menu_th), getString(R.string.logout_menu_th)};
        int[] arrImg = {R.drawable.ic_news_feed,R.drawable.ic_user_profile,  R.drawable.ic_edit_user, R.drawable.ic_setting,R.drawable.ic_log_out};
        for (int i = 0; i < arrTitle.length; i++){
            SlideMenuDetail menu = new SlideMenuDetail();
            menu.setTitle(arrTitle[i]);
            menu.setImage(arrImg[i]);
            arrMenu.add(menu);
        }

        SlideMenuAdapter slideMenuAdapter = new SlideMenuAdapter(this, arrMenu) {
            @Override
            public void onBindViewHolder(SlideMenuHolder holder, int position) {
                holder.subTitle.setText(getTitle(position));
                holder.imgIcon.setImageResource(getImage(position));
                holder.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (getTitle(position).equalsIgnoreCase(getString(R.string.user_detail_th))){
                            openActivity(ProfileActivity.class);
                            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_scale_out);
                        }
                        else if (getTitle(position).equalsIgnoreCase(getString(R.string.menu_feed_th))){
                            callHomeFragment();
                            setToggle(0,0);
                        }
                    }
                });
            }
        };
        listSlideMenu.setLayoutManager(new LinearLayoutManager(this));
        listSlideMenu.setAdapter(slideMenuAdapter);
        listSlideMenu.setItemAnimator(new DefaultItemAnimator());
//        listSlideMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    //<=======================================================================

    private void callHomeFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new HomeFragment());
        transaction.commit();
    }
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_toggle){
            setToggle(0,0);
        }
    }
}
