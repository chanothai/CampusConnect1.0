package com.company.zicure.campusconnect.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.fragment.IDCardFragment;
import com.company.zicure.campusconnect.network.ClientHttp;
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
    private String userId = null;
    private String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);
        EventBusCart.getInstance().getEventBus().register(this);
        bindView();
        iniBundle();

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
            userId = bundle.getString("user_id");
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        setToolbar();

        token = ModelCart.getInstance().getKeyModel().getToken();
        showLoadingDialog();
        ClientHttp.getInstance(this).requestProfile(token, userId);
    }

    private void setToolbar(){
        if (Build.VERSION.SDK_INT >= 21) {
            ToolbarManager manager = new ToolbarManager(this);
            manager.setToolbar(toolbar, textTitle, getDrawable(R.drawable.back_screen), titleBloc);
        }
    }

    //EventBus
    @Subscribe
    public void onEventResponseIDCard(ResponseIDCard response){
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            ModelCart.getInstance().setProfile(response.getResult().getData());
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
