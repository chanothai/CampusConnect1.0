package com.company.zicure.registerkey.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.fragment.IDCardFragment;
import com.company.zicure.registerkey.network.ClientHttp;
import com.squareup.otto.Subscribe;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.profile.ResponseIDCard;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.ToolbarManager;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class IDCardActivity extends BaseActivity {

    //Make: view
    private Toolbar toolbar = null;
    private TextView textTitle = null;

    //Make: Properties
    private String titleBloc = null;
    private String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);
        EventBusCart.getInstance().getEventBus().register(this);
        bindView();
        iniBundle();

        token = ModelCart.getInstance().getKeyModel().getToken();
        if (token != null){
            showLoadingDialog();
            ClientHttp.getInstance(this).requestProfile(token);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    private void bindView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_bloc);
        textTitle = (TextView) toolbar.findViewById(R.id.text_title);
    }

    private void iniBundle(){
        try{
            Bundle bundle = getIntent().getExtras();
            titleBloc = bundle.getString(VariableConnect.TITLE_CATEGORY);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        setToolbar();
    }

    private void setToolbar(){
        if (Build.VERSION.SDK_INT >= 21) {
            ToolbarManager manager = new ToolbarManager(this);
            manager.setToolbar(toolbar, textTitle, null, titleBloc);
        }
    }

    //EventBus
    @Subscribe
    public void onEventResponseIDCard(ResponseIDCard response){
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            ModelCart.getInstance().setProfile(response.getResult().getData().get(0));
            iniFragmentIDCard();
        }

        dismissDialog();
    }

    private void iniFragmentIDCard(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_bloc, new IDCardFragment());
        transaction.commit();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out_right);
    }
}
