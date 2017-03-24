package com.company.zicure.registerkey;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.company.zicure.registerkey.activity.CheckLoginActivity;
import com.company.zicure.registerkey.common.BaseActivity;
import com.company.zicure.registerkey.models.BaseResponse;
import com.company.zicure.registerkey.network.ClientHttp;
import com.company.zicure.registerkey.utilize.EventBusCart;
import com.company.zicure.registerkey.variables.VariableConnect;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends BaseActivity implements Animator.AnimatorListener{


    @Bind(R.id.img_logo)
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);
        EventBusCart.getInstance().getEventBus().register(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ClientHttp.getInstance(this).checkVersionApp();
    }

    @Subscribe
    public void onEventCheckVersion(BaseResponse response){
        try{
            if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
                if (response.getResult().getData().getVersion().equalsIgnoreCase(VariableConnect.versionAndroid)){
                    animationFadeOut();
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
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
