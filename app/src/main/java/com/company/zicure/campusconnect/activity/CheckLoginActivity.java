package com.company.zicure.campusconnect.activity;

import android.os.Bundle;
import com.company.zicure.campusconnect.R;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;

public class CheckLoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);

        if (savedInstanceState == null) {
            checkLogin();
        }
    }


    private void checkLogin(){
        Bundle bundle = getIntent().getExtras();
        String[] strArr = bundle.getStringArray(getString(R.string.user_secret));
        if (strArr != null) {
            openActivity(MainMenuActivity.class, bundle, true);
        }
    }
}
