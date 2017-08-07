package com.company.zicure.campusconnect.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.network.ClientHttp;
import com.company.zicure.campusconnect.utility.PermissionKeyNumber;
import com.company.zicure.campusconnect.utility.PermissionRequest;
import com.company.zicure.campusconnect.utility.RestoreLogin;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class SplashScreenActivity extends BaseActivity implements Animator.AnimatorListener{

    private String authCode = null;
    private PermissionRequest permissionRequest = null;

    private String currentToken = null;
    private String currentDynamicKey = null;
    private String currentUsername = null;
    private byte[] key = null;

    @Bind(R.id.img_logo)
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);
        EventBusCart.getInstance().getEventBus().register(this);
        ButterKnife.bind(this);

        permissionRequest = new PermissionRequest(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ClientHttp.getInstance(this).checkVersionApp();
    }

    @Subscribe
    public void onEventCheckVersion(BaseResponse response){
        try{
            if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
                if (response.getResult().getData().getVersion().equalsIgnoreCase(VariableConnect.versionAndroid)){
                    if (!permissionRequest.requestReadStorage()){
                        animationFadeOut();
                    }
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            dismissDialog();
        }
    }

    public void animationFadeOut() {
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animatorFadeIn = ObjectAnimator.ofFloat(imgLogo, View.ALPHA, 1f);
        ObjectAnimator animatorFadeOut = ObjectAnimator.ofFloat(imgLogo, View.ALPHA, 0f);
        animSet.playSequentially(animatorFadeIn, animatorFadeOut);
        animSet.setDuration(1000);
        animSet.addListener(this);
        animSet.start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionKeyNumber.getInstance().getPermissionReadStorageKey() == requestCode){
            if (grantResults[0] != -1){
                animationFadeOut();
            }
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        checkLogin();
    }

    private void checkLogin(){
        currentToken = RestoreLogin.getInstance(this).getRestoreToken();
        currentDynamicKey = RestoreLogin.getInstance(this).getRestoreKey();
        currentUsername = RestoreLogin.getInstance(this).getRestoreUser();

        if (currentDynamicKey != null && currentToken != null && currentUsername != null && !currentUsername.isEmpty()){
            Bundle bundle = new Bundle();
            String[] strArr = new String[3];
            strArr[0] = currentToken;
            strArr[1] = currentUsername;
            strArr[2] = currentDynamicKey;

            bundle.putStringArray(getString(R.string.user_secret), strArr);
            openActivity(MainMenuActivity.class, bundle, true);
        }else{
            openActivity(LoginActivity.class, true);
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }
}
