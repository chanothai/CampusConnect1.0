package com.company.zicure.registerkey;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.zicure.registerkey.activity.CheckLoginActivity;
import com.company.zicure.registerkey.network.ClientHttp;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import gallery.zicure.company.com.gallery.util.PermissionKeyNumber;
import gallery.zicure.company.com.gallery.util.PermissionRequest;
import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.security.EncryptionAES;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class SplashScreenActivity extends BaseActivity implements Animator.AnimatorListener{

    private String authCode = null;
    private PermissionRequest permissionRequest = null;

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

    public void animationFadeOut(){
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animatorFadeIn = ObjectAnimator.ofFloat(imgLogo, View.ALPHA, 1f);
        ObjectAnimator animatorFadeOut = ObjectAnimator.ofFloat(imgLogo, View.ALPHA, 0f);
        animSet.playSequentially(animatorFadeIn, animatorFadeOut);
        animSet.setDuration(1000);
        animSet.addListener(this);
        animSet.start();
    }

    public void intentActivity(){
        openActivity(CheckLoginActivity.class , true);
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
        intentActivity();
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
