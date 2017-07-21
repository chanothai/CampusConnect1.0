package com.company.zicure.registerkey.contents;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.activity.BlocContentActivity;
import com.company.zicure.registerkey.activity.LoginActivity;
import com.company.zicure.registerkey.adapter.MainMenuAdapter;
import com.company.zicure.registerkey.adapter.SlideMenuAdapter;
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
    private BaseActivity baseActivity = null;
    private Activity atv = null;

    public ContentAdapterCart(){

    }

    public SlideMenuAdapter setSlideMenuAdapter(BaseActivity activity, ArrayList<SlideMenuDetail> arrMenu){
        baseActivity = activity;
        SlideMenuAdapter slideMenuAdapter = new SlideMenuAdapter(activity, arrMenu) {
            @Override
            public void onBindViewHolder(SlideMenuHolder holder, int position) {
                holder.subTitle.setText(getTitle(position));
                holder.imgIcon.setImageResource(getImage(position));
                holder.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (getTitle(position).equalsIgnoreCase(baseActivity.getString(R.string.user_detail_th))){
                            Bundle bundle = new Bundle();
                            bundle.putInt(VariableConnect.pageKey, 0);
                            baseActivity.openActivity(ProfileActivity.class, bundle);
                            baseActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_scale_out);
                        }
                        else if (getTitle(position).equalsIgnoreCase(baseActivity.getString(R.string.menu_feed_th))){
                            baseActivity.showLoadingDialog();
                            ClientHttp.getInstance(baseActivity).requestUserBloc(ModelCart.getInstance().getAuth().getAuthToken());
                        }
                        else if (getTitle(position).equalsIgnoreCase(baseActivity.getString(R.string.logout_menu_th))){
//                            AppMenuFragment fragment = (AppMenuFragment.newInstance(""));
//                            fragment.clearCookies();

                            SharedPreferences pref = baseActivity.getSharedPreferences(VariableConnect.keyFile, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.clear();
                            editor.commit();

                            baseActivity.openActivity(LoginActivity.class, true);
                        }
                        else if (getTitle(position).equalsIgnoreCase(baseActivity.getString(R.string.activate_user_th))){
                            Bundle bundle = new Bundle();
                            bundle.putInt(VariableConnect.pageKey, 1);
                            baseActivity.openActivity(ProfileActivity.class, bundle);
                            baseActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_scale_out);
                        }else if (getTitle(position).equalsIgnoreCase(baseActivity.getString(R.string.id_card_th))){
                            Bundle bundle = new Bundle();
                            bundle.putString(VariableConnect.TITLE_CATEGORY, baseActivity.getString(R.string.id_card_th));
                            bundle.putString(VariableConnect.PATH_BLOC, "");
                            baseActivity.openActivity(BlocContentActivity.class, bundle, false);
                            baseActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_scale_out);
                        }
                    }
                });
            }
        };
        return slideMenuAdapter;
    }

    public MainMenuAdapter setMainMenuAdapter(Activity activity, final List<CategoryModel.Result.Data.Bloc> arrBloc) {
        atv = activity;
        //set adapter
         MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(activity,arrBloc) {
            //Data is bound to view
            @Override
            public void onBindViewHolder(final MainMenuHolder holder, int position) {
                holder.topicMenu.setText(getData().get(position).getBlocName());
                Glide.with(atv)
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
                        Intent intent = new Intent(atv, BlocContentActivity.class);
                        intent.putExtras(bundle);
                        atv.startActivity(intent);
                        atv.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_scale_out);
                    }
                });
            }
        };

        return mainMenuAdapter;
    }
}