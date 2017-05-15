package com.company.zicure.registerkey.activity;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.fragment.AppMenuFragment;
import com.company.zicure.registerkey.fragment.IDCardFragment;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.utilize.NextzyUtil;
import gallery.zicure.company.com.modellibrary.utilize.ToolbarManager;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class BlocContentActivity extends BaseActivity {

    private Toolbar toolbar = null;
    private TextView textTitle = null;

    private String titleBloc = null;
    private String urlBloc = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloc_content);
        bindView();

        if (savedInstanceState == null) {
            iniBundle();
            setToolbar();
            checkIniFragment();
        }
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
        if (!urlBloc.equalsIgnoreCase("")){
            iniFragmentBloc();
        }else{
            iniFragmentIDCard();
        }
    }

    private void iniFragmentIDCard(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_bloc, new IDCardFragment());
        transaction.commit();
    }

    private void iniFragmentBloc(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_bloc, AppMenuFragment.newInstance(urlBloc), VariableConnect.appMenuFragmentKey);
        transaction.commit();
    }

    private void bindView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_bloc);
        textTitle = (TextView) findViewById(R.id.text_title);
    }

    private void setToolbar(){
        if (Build.VERSION.SDK_INT >= 21) {
            ToolbarManager manager = new ToolbarManager(this);
            manager.setToolbar(toolbar, textTitle, titleBloc);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                if (!urlBloc.equalsIgnoreCase("")){
                    overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out_right);
                }else{
                    overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out_left);
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
