package com.company.zicure.registerkey;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.company.zicure.registerkey.common.BaseActivity;
import com.company.zicure.registerkey.utilize.NextzyUtil;
import com.company.zicure.registerkey.utilize.PermissionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SlashScreenActivity extends BaseActivity implements Animator.AnimatorListener{


    @Bind(R.id.img_logo)
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        animationFadeOut();
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
        openActivity(LoginActivity.class , true);
        overridePendingTransition(R.anim.anim_slide_in_top,R.anim.anim_slide_out_top);
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
}
