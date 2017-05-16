package com.company.zicure.registerkey;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
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
import com.company.zicure.registerkey.contents.ContentAdapterCart;
import com.company.zicure.registerkey.fragment.AppMenuFragment;
import com.company.zicure.registerkey.fragment.HomeFragment;
import com.company.zicure.registerkey.fragment.ScanQRFragment;

import gallery.zicure.company.com.modellibrary.models.CategoryModel;
import com.company.zicure.registerkey.network.ClientHttp;
import com.company.zicure.registerkey.security.EncryptionAES;
import com.company.zicure.registerkey.view.viewgroup.FlyOutContainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import gallery.zicure.company.com.gallery.util.PermissionKeyNumber;
import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.ApplicationRequest;
import gallery.zicure.company.com.modellibrary.models.AuthToken;
import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import gallery.zicure.company.com.modellibrary.models.ResponseUserInfo;
import gallery.zicure.company.com.modellibrary.models.drawer.SlideMenuDetail;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.ResizeScreen;
import gallery.zicure.company.com.modellibrary.utilize.ToolbarManager;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class MainMenuActivity extends BaseActivity implements  View.OnClickListener {

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

    //view layout
    private FlyOutContainer root;

    //fragment layout
    private ScanQRFragment scanQRFragment = null;

    private static final int PORT = 5055;
    private static final String DEVICEID = "218989";

    private int widthScreenMenu;
    private int haftScreen = 0;
    private VelocityTracker velocityTracker = null; // get speed for touch

    private DataModel model = null;
    private byte[] key = null;
    private String currentToken = null;
    private String currentUsername = null;
    private String[] strArr = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = (FlyOutContainer) getLayoutInflater().inflate(R.layout.activity_main_menu, null);
        setContentView(root);
        ButterKnife.bind(this);
        setToolbar();

        setOnTouchView();

        if (savedInstanceState == null){
            key = ModelCart.getInstance().getKeyModel().getKey();
            Bundle bundle = getIntent().getExtras();
            strArr = bundle.getStringArray(getString(R.string.user_secret));
            currentToken = strArr[0];
            currentUsername = strArr[1];

            if (currentUsername != null && currentToken != null){
                setModelUser();
            }
        }
    }

    public void setToolbar(){
        if (Build.VERSION.SDK_INT >= 21){
            ToolbarManager manager = new ToolbarManager(this);
            manager.setToolbar(toolbarMenu,null, getDrawable(R.drawable.menu_toggle), null);

            setLayoutHeadDrawer();
        }else{
            appBarLayout.setVisibility(View.GONE);
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
            ApplicationRequest request = new ApplicationRequest();
            ApplicationRequest.Application application = new ApplicationRequest.Application();
            application.setClientID(VariableConnect.clientID);
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

                showLoadingDialog();
                ClientHttp.getInstance(this).requestAuthToken(model);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            dismissDialog();
        }
    }

    @Subscribe
    public void onEventAuthToken(BaseResponse baseResponse){
        try {
            if (baseResponse.getResult().getSuccess().equalsIgnoreCase("OK")){
                String[] splitKey = baseResponse.getResult().geteResult().split(getString(R.string.key_iv));
                String decrypt = EncryptionAES.newInstance(key).decrypt(splitKey[0], splitKey[1].getBytes());
                decodeJson(decrypt);
            } else {
                Toast.makeText(this, baseResponse.getResult().getError(), Toast.LENGTH_SHORT).show();
                openActivity(LoginActivity.class, true);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            dismissDialog();
        }
    }

    private void decodeJson(String decrypt){
        try{
            JSONObject jsonObject = new JSONObject(decrypt);
            String success = jsonObject.getString("Success");
            if (success.equalsIgnoreCase("OK")){
                jsonObject = jsonObject.getJSONObject("AuthToken");

                ModelCart.getInstance().getAuth().setAuthToken(jsonObject.getString("auth_token"));
                ModelCart.getInstance().getAuth().setExpiryDate(jsonObject.getString("auth_token_expiry_date"));

                validateCurrentDate(ModelCart.getInstance().getAuth());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void validateCurrentDate(AuthToken authToken){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            Date strDate = dateFormat.parse(authToken.getExpiryDate());

            if (date.after(strDate)){

            }else {
                String path = authToken.getAuthToken();
                ClientHttp.getInstance(this).requestUserInfo(path);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEventGetUserInfo(ResponseUserInfo response){
        try{
            if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
                Log.d("UserInfo", new Gson().toJson(response.getResult()));
                ModelCart.getInstance().getUserInfo().setResult(response.getResult());

                setSlideMenuAdapter();

                ClientHttp.getInstance(this).requestUserBloc(ModelCart.getInstance().getAuth().getAuthToken());
            }else{
                Toast.makeText(this, response.getResult().getError(), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            dismissDialog();
        }
    }

    @Subscribe
    public void onEventResponseUserBloc(CategoryModel response){
        try{
            if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
                ModelCart.getInstance().getCategoryModel().setResult(response.getResult());
                callHomeFragment();

                if (FlyOutContainer.menuCurrentState == FlyOutContainer.MenuState.OPEN){
                    setToggle(0,0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        dismissDialog();
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
        String pathImg = ModelCart.getInstance().getUserInfo().getResult().getData().getUser().getImgPath();
        Glide.with(this)
                .load(pathImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imgProfile);

        profileName.setText(ModelCart.getInstance().getUserInfo().getResult().getData().getUser().getScreenName());

        arrMenu = new ArrayList<>();
        Log.d("SlideMenu",new Gson().toJson(arrMenu));
        String[] arrTitle = {
                getString(R.string.menu_feed_th),
                getString(R.string.user_detail_th),
                getString(R.string.id_card_th),
                getString(R.string.activate_user_th),
                getString(R.string.logout_menu_th),};

        int[] arrImg = {
                R.drawable.ic_news_feed,
                R.drawable.ic_person,
                R.drawable.idcard,
                R.drawable.opensign,
                R.drawable.ic_log_out};
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
//        listSlideMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
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
        else if (item.getItemId() == R.id.action_payment){
            intentToPayment();
        }

        return super.onOptionsItemSelected(item);
    }

    private void intentToPayment(){
        String authToken = null;
        String strPackage = "com.company.zicure.payment";
        try{
            authToken = ModelCart.getInstance().getKeyModel().getAuthToken();
            Intent intent = getPackageManager().getLaunchIntentForPackage(strPackage);
            if (authToken != null){
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, authToken);
            }
            startActivity(intent);
            ModelCart.getInstance().getKeyModel().setAuthToken("");

        }catch (NullPointerException e){
            e.printStackTrace();
            try{
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + strPackage)));
            }catch (ActivityNotFoundException ef){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + strPackage)));
            }
        }
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
    public void onClick(View view) {

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
