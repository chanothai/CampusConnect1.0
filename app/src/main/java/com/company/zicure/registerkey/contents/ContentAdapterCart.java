package com.company.zicure.registerkey.contents;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.company.zicure.registerkey.LoginActivity;
import com.company.zicure.registerkey.MainMenuActivity;
import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.adapter.SlideMenuAdapter;
import com.company.zicure.registerkey.holder.SlideMenuHolder;
import com.company.zicure.registerkey.interfaces.ItemClickListener;

import java.util.ArrayList;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.drawer.SlideMenuDetail;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;
import profilemof.zicure.company.com.profilemof.activity.ProfileActivity;

/**
 * Created by 4GRYZ52 on 4/1/2017.
 */

public class ContentAdapterCart {
    private BaseActivity activity = null;
    public ContentAdapterCart(BaseActivity activity){
        this.activity = activity;
    }

    public SlideMenuAdapter setSlideMenuAdapter(ArrayList<SlideMenuDetail> arrMenu){
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
                            ((MainMenuActivity)activity).setModelUser();
                            ((MainMenuActivity)activity).callHomeFragment();
                            ((MainMenuActivity)activity).setToggle(0,0);
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


}