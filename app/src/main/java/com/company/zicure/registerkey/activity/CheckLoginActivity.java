package com.company.zicure.registerkey.activity;

import android.os.Bundle;
import android.util.Base64;

import com.company.zicure.registerkey.LoginActivity;
import com.company.zicure.registerkey.MainMenuActivity;
import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.RegisterActivity;
import com.company.zicure.registerkey.RestoreLogin;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;

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
