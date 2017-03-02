package com.company.zicure.registerkey.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.zicure.registerkey.MainMenuActivity;
import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.dialog.LoadingDialogFragment;
import com.company.zicure.registerkey.fragment.HomeFragment;
import com.company.zicure.registerkey.view.viewgroup.FlyOutContainer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 4GRYZ52 on 10/21/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG_DIALOG_FRAGMENT = "dialog_fragment";
    private LoadingDialogFragment loadingDialogFragment = null;


    protected void openActivity(Class<?> cls) {
        openActivity(cls,null, false);
    }

    protected void openActivity(Class<?> cls, boolean finishActivity) {
        openActivity(cls,null, finishActivity);
    }

    protected void openActivity(Class<?> cls, Bundle bundle) {
        openActivity(cls, bundle, false);
    }

    protected void openActivity(Class<?> cls,Bundle bundle, boolean finishActivity) {
        if (cls != null){
            Intent intent = new Intent(this, cls);
            if (bundle != null){
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
        if (finishActivity){
            finish();
        }
    }

    public void showLoadingDialog(){
        dismissDialog();
        loadingDialogFragment = new LoadingDialogFragment.Builder().build();
        createFragmentDialog(loadingDialogFragment);
    }

    private void createFragmentDialog(DialogFragment dialogFragment){
        try{
            dialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_FRAGMENT);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    public void dismissDialog(){
        try{
            if (loadingDialogFragment != null){
                loadingDialogFragment.dismiss();
            }
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }
}
