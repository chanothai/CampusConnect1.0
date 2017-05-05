package com.company.zicure.registerkey.contents;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.registerkey.LoginActivity;
import com.company.zicure.registerkey.MainMenuActivity;
import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.activity.BlocContentActivity;
import com.company.zicure.registerkey.adapter.MainMenuAdapter;
import com.company.zicure.registerkey.adapter.SlideMenuAdapter;
import com.company.zicure.registerkey.fragment.AppMenuFragment;
import com.company.zicure.registerkey.holder.MainMenuHolder;
import com.company.zicure.registerkey.holder.SlideMenuHolder;
import com.company.zicure.registerkey.interfaces.ItemClickListener;
import com.company.zicure.registerkey.network.ClientHttp;

import java.util.ArrayList;
import java.util.List;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.CategoryModel;
import gallery.zicure.company.com.modellibrary.models.drawer.SlideMenuDetail;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.ResizeScreen;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;
import profilemof.zicure.company.com.profilemof.activity.ProfileActivity;

/**
 * Created by 4GRYZ52 on 4/1/2017.
 */

public class ContentAdapterCart {
    public ContentAdapterCart(){

    }

    public SlideMenuAdapter setSlideMenuAdapter(final BaseActivity activity,ArrayList<SlideMenuDetail> arrMenu){
        SlideMenuAdapter slideMenuAdapter = new SlideMenuAdapter(activity, arrMenu) {
            @Override
            public void onBindViewHolder(SlideMenuHolder holder, int position) {
                holder.subTitle.setText(getTitle(position));
                holder.imgIcon.setImageResource(getImage(position));
                holder.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (getTitle(position).equalsIgnoreCase(activity.getString(R.string.user_detail_th))){
                            Bundle bundle = new Bundle();
                            bundle.putInt(VariableConnect.pageKey, 0);
                            activity.openActivity(ProfileActivity.class, bundle);
                            activity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_scale_out);
                        }
                        else if (getTitle(position).equalsIgnoreCase(activity.getString(R.string.menu_feed_th))){
                            activity.showLoadingDialog();
                            ClientHttp.getInstance(activity).requestUserBloc(ModelCart.getInstance().getAuth().getAuthToken());
                        }
                        else if (getTitle(position).equalsIgnoreCase(activity.getString(R.string.logout_menu_th))){
                            SharedPreferences pref = activity.getSharedPreferences(VariableConnect.keyFile, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.clear();
                            editor.commit();

                            activity.openActivity(LoginActivity.class, true);
                        }
                        else if (getTitle(position).equalsIgnoreCase(activity.getString(R.string.activate_user_th))){
                            Bundle bundle = new Bundle();
                            bundle.putInt(VariableConnect.pageKey, 1);
                            activity.openActivity(ProfileActivity.class, bundle);
                            activity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_scale_out);
                        }
                    }
                });
            }
        };
        return slideMenuAdapter;
    }

    public MainMenuAdapter setMainMenuAdapter(final Activity activity, final Fragment fragment, final List<CategoryModel.Result.Data.Bloc> arrBloc) {
        //set adapter
         MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(activity,arrBloc) {
            //Data is bound to view
            @Override
            public void onBindViewHolder(final MainMenuHolder holder, int position) {
                holder.topicMenu.setText(getData().get(position).getBlocName());
                Glide.with(activity)
                        .load(getData().get(position).getBlocIconPath())
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imgBtnMenu);

                holder.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString(VariableConnect.TITLE_CATEGORY, getData().get(position).getBlocName());
                        bundle.putString(VariableConnect.PATH_BLOC, getData().get(position).getBlocUrl());
                        Intent intent = new Intent(activity, BlocContentActivity.class);
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_scale_out);
                    }
                });
            }
        };

        return mainMenuAdapter;
    }

    /*
    private void intentToMOFPAY(){
        String authToken = null;
        String strPackage = "com.company.zicure.payment";
        try{
            authToken = ModelCart.getInstance().getKeyModel().getAuthToken();
            Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(strPackage);
            if (authToken != null){
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, authToken);
            }
            activity.startActivity(intent);
            ModelCart.getInstance().getKeyModel().setAuthToken("");

        }catch (NullPointerException e){
            e.printStackTrace();
            try{
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + strPackage)));
            }catch (ActivityNotFoundException ef){
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + strPackage)));
            }
        }
    }
    */

}