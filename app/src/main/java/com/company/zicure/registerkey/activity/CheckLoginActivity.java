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
    private String currentToken = null;
    private String currentDynamicKey = null;
    private String currentUsername = null;
    private byte[] key = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);

        checkLogin();
    }


    private void checkLogin(){
        currentToken = RestoreLogin.getInstance(this).getRestoreToken();
        currentDynamicKey = RestoreLogin.getInstance(this).getRestoreKey();
        currentUsername = RestoreLogin.getInstance(this).getRestoreUser();

        if (currentDynamicKey != null && currentToken != null && currentUsername != null && !currentUsername.isEmpty()){
            key = Base64.decode(currentDynamicKey.getBytes(), Base64.NO_WRAP);
            ModelCart.getInstance().getKeyModel().setKey(key);
            ModelCart.getInstance().getKeyModel().setUsername(currentUsername);
            ModelCart.getInstance().getKeyModel().setToken(currentToken);

            Bundle bundle = new Bundle();
            String[] strArr = {currentToken, currentUsername};
            bundle.putStringArray(getString(R.string.user_secret), strArr);
            openActivity(MainMenuActivity.class, bundle, true);
        }else{
            openActivity(LoginActivity.class, true);
        }
    }
}
