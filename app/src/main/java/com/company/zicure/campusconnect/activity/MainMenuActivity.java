package com.company.zicure.campusconnect.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.adapter.SlideMenuAdapter;
import com.company.zicure.campusconnect.contents.ContentAdapterCart;
import com.company.zicure.campusconnect.customView.LabelView;
import com.company.zicure.campusconnect.fragment.AppMenuFragment;
import com.company.zicure.campusconnect.fragment.HomeFragment;

import com.company.zicure.campusconnect.network.ClientHttp;
import com.company.zicure.campusconnect.utility.PermissionKeyNumber;
import com.company.zicure.campusconnect.view.viewgroup.FlyOutContainer;
import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import gallery.zicure.company.com.modellibrary.models.bloc.ResponseBlocUser;
import gallery.zicure.company.com.modellibrary.models.drawer.SlideMenuDetail;
import gallery.zicure.company.com.modellibrary.models.quiz.ResponseQuiz;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.ResizeScreen;
import gallery.zicure.company.com.modellibrary.utilize.ToolbarManager;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class MainMenuActivity extends BaseActivity {

    /** Make: View **/
    //layout coordinator cover material layout
    @Bind(R.id.liner_content)
    RelativeLayout linearLayout;
    @Bind(R.id.layout_menu)
    RelativeLayout layoutMenu;

    //toolbar
    @Bind(R.id.toolbar)
    Toolbar toolbarMenu;
    @Bind(R.id.point)
    LabelView point;

    //list view slide menu
    @Bind(R.id.list_slide_menu)
    RecyclerView listSlideMenu;
    @Bind(R.id.layout_ghost)
    FrameLayout layoutGhost;
    @Bind(R.id.control_slide)
    FrameLayout controlSlide;
    @Bind(R.id.img_profile)
    SelectableRoundedImageView imgProfile;
    @Bind(R.id.profile_name)
    TextView profileName;

    //list slide menu
    private ArrayList<SlideMenuDetail> arrMenu = null;
    @Bind(R.id.child_header_drawer)
    RelativeLayout childHeaderDrawer;
    @Bind(R.id.header_drawer)
    RelativeLayout headerDrawer;

    /** Make: properties **/
    //view layout
    private FlyOutContainer root;
    private int widthScreenMenu;
    int haftScreen = 0;
    private VelocityTracker velocityTracker = null; // get speed for touch
    private DataModel model = null;
    byte[] key = null;
    private String currentToken = null;
    String currentUsername = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = (FlyOutContainer) getLayoutInflater().inflate(R.layout.activity_main_menu, null);
        setContentView(root);
        ButterKnife.bind(this);
        setToolbar();
        setOnTouchView();

    }

    private void initParameter(){
        Bundle bundle = getIntent().getExtras();
        String[] strArr = bundle.getStringArray(getString(R.string.user_secret));
        if (strArr != null) {
            currentToken = strArr[0];
            currentUsername = strArr[1];
            key = Base64.decode(strArr[2], Base64.NO_WRAP);

            if (currentUsername != null && currentToken != null){
                // set data for profile activity
                ModelCart.getInstance().getKeyModel().setToken(currentToken);
                ModelCart.getInstance().getKeyModel().setKey(key);
                ModelCart.getInstance().getKeyModel().setUsername(currentUsername);

                setModelUser();
            }
        }
    }

    public void setToolbar(){
        if (Build.VERSION.SDK_INT >= 21){
            ToolbarManager manager = new ToolbarManager(this);
            manager.setToolbar(toolbarMenu,null, getDrawable(R.drawable.menu_toggle), null);
            setLayoutHeadDrawer();
        }
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

    private int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusCart.getInstance().getEventBus().register(this);
        initParameter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    public void callHomeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new HomeFragment());
        transaction.commit();
    }

    public void setModelUser(){
        try{
            showLoadingDialog();
            ClientHttp.getInstance(this).requestUserBloc(currentToken);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /** onEvent **/
    @Subscribe
    public void onEventResponseUserBloc(ResponseBlocUser response){
        try{
            if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
                String resultPoint = Integer.toString(response.getResult().getData().getUserInfo().getPoint());
                point.setText(resultPoint);
                ModelCart.getInstance().getUserBloc().setResult(response.getResult());
                callHomeFragment();
                setSlideMenuAdapter();

                if (FlyOutContainer.menuCurrentState == FlyOutContainer.MenuState.OPEN){
                    setToggle(0,0);
                }

                ClientHttp.getInstance(this).requestQuiz(currentToken);
            }else{
                dismissDialog();
                SharedPreferences pref = getSharedPreferences(VariableConnect.keyFile, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                openActivity(LoginActivity.class, true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEventResponseQuiz(ResponseQuiz response) {
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            if (response.getResult().getData().getPersonalQuiz().getUrl() != null){
                Bundle bundle = new Bundle();
                bundle.putString(VariableConnect.TITLE_CATEGORY, "แบบสอบถาม");
                bundle.putString(VariableConnect.PATH_BLOC, response.getResult().getData().getPersonalQuiz().getUrl());
                openActivity(BlocContentActivity.class, bundle);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        }

        dismissDialog();
    }

    /************/

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

    public void setSlideMenuAdapter(){
        String pathImg = ModelCart.getInstance().getUserBloc().getResult().getData().getUserInfo().getImgPath();
        Glide.with(this)
                .load(pathImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imgProfile);

        String screenName = ModelCart.getInstance().getUserBloc().getResult().getData().getUserInfo().getFirstNameTH() + " " + ModelCart.getInstance().getUserBloc().getResult().getData().getUserInfo().getLastNameTH();
        profileName.setText(screenName);

        arrMenu = new ArrayList<>();
        Log.d("SlideMenu",new Gson().toJson(arrMenu));
        String[] arrTitle = {
                getString(R.string.menu_feed_th),
                getString(R.string.payment_th),
                getString(R.string.logout_menu_th)};

        int[] arrImg = {
                R.drawable.ic_news_feed,
                R.drawable.point,
                R.drawable.exit};

        for (int i = 0; i < arrTitle.length; i++){
            SlideMenuDetail menu = new SlideMenuDetail();
            menu.setTitle(arrTitle[i]);
            menu.setImage(arrImg[i]);
            arrMenu.add(menu);
        }

        ContentAdapterCart adapterCart = new ContentAdapterCart();
        SlideMenuAdapter slideMenuAdapter = adapterCart.setSlideMenuAdapter(this,arrMenu);
        listSlideMenu.setLayoutManager(new LinearLayoutManager(this));
        listSlideMenu.setAdapter(slideMenuAdapter);
        listSlideMenu.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setToggle(0,0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (FlyOutContainer.menuCurrentState == FlyOutContainer.MenuState.OPEN){
            setToggle(0,0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionKeyNumber.getInstance().getPermissionCameraKey() == requestCode){
            if (grantResults[0] != -1){
                FragmentManager fm = getSupportFragmentManager();
                AppMenuFragment fragment = (AppMenuFragment) fm.findFragmentByTag(VariableConnect.appMenuFragmentKey);
                fragment.setWebView();
            }
        }
    }
}
