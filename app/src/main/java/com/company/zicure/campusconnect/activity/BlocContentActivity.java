package com.company.zicure.campusconnect.activity;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.fragment.AddFriendFragment;
import com.company.zicure.campusconnect.fragment.AppMenuFragment;
import com.squareup.otto.Subscribe;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.contact.ResponseAddContact;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ToolbarManager;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class BlocContentActivity extends BaseActivity {
    private FrameLayout frameLayout = null;
    private Toolbar toolbar = null;
    private TextView textTitle = null;

    private String titleBloc = null;
    private String urlBloc = null;

    private AppMenuFragment appMenuFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloc_content);
        EventBusCart.getInstance().getEventBus().register(this);
        bindView();
        iniBundle();
        setToolbar();

        if (savedInstanceState == null) {
            checkIniFragment();
        }
    }

    private void bindView(){
        frameLayout = (FrameLayout) findViewById(R.id.container_bloc);
        toolbar = (Toolbar) findViewById(R.id.toolbar_bloc);
        textTitle = (TextView) toolbar.findViewById(R.id.text_title);
    }

    private void iniBundle(){
        try{
            Bundle bundle = getIntent().getExtras();
            titleBloc = bundle.getString(VariableConnect.TITLE_CATEGORY);
            urlBloc = bundle.getString(VariableConnect.PATH_BLOC);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void checkIniFragment(){
        if (!urlBloc.isEmpty()){
            iniFragmentBloc();
        }else{
            initFragmentAddFriend();
        }
    }

    private void iniFragmentBloc(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_bloc, AppMenuFragment.newInstance(urlBloc), VariableConnect.appMenuFragmentKey);
        transaction.commit();
    }

    private void initFragmentAddFriend(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_bloc, AddFriendFragment.newInstance("",""), "AddFriendFragment");
        transaction.commit();
    }

    private void setToolbar(){
        if (Build.VERSION.SDK_INT >= 21) {
            ToolbarManager manager = new ToolbarManager(this);
            manager.setToolbar(toolbar, textTitle, getDrawable(R.drawable.back_screen), titleBloc);

            if (urlBloc.isEmpty()){
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) frameLayout.getLayoutParams();
                params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
                frameLayout.requestLayout();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out_right);
                break;
            }

            default: {
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        appMenuFragment = (AppMenuFragment.newInstance(urlBloc));
        appMenuFragment.saveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        appMenuFragment = (AppMenuFragment.newInstance(urlBloc));
        appMenuFragment.restoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appMenuFragment = (AppMenuFragment.newInstance(urlBloc));
        appMenuFragment.clearCache();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    /** onEvent **/
    @Subscribe
    public void onEventResponseAddContact(ResponseAddContact response) {
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            Toast.makeText(this, response.getResult().getMessage(), Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out_right);
        }else{
            Toast.makeText(this, response.getResult().getError(), Toast.LENGTH_SHORT).show();
            resumeCamera();
        }

        dismissDialog();
    }

    private void resumeCamera() {
        FragmentManager fm = getSupportFragmentManager();
        AddFriendFragment fragment = (AddFriendFragment) fm.findFragmentByTag("AddFriendFragment");
        fragment.resumeCamera();
    }
}
