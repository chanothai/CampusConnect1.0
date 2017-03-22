package com.company.zicure.registerkey.utilize;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.company.zicure.registerkey.R;

/**
 * Created by 4GRYZ52 on 3/22/2017.
 */

public class RestoreLogin {
    private static RestoreLogin me = null;
    private Activity activity = null;
    private SharedPreferences pref = null;

    public RestoreLogin(Activity activity){
        this.activity = activity;
        pref = activity.getSharedPreferences(activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public static RestoreLogin getInstance(Activity activity){
        if (me == null){
            me = new RestoreLogin(activity);
        }
        return me;
    }

    public String getRestoreToken(){
        String token = pref.getString(activity.getString(R.string.token_login), null);
        return token;
    }

    public String getRestoreUser(){
        String user = pref.getString(activity.getString(R.string.username), null);
        return user;
    }

    public String getRestoreKey(){
        String key = pref.getString(activity.getString(R.string.dynamic_key), null);
        return key;
    }

    public String getRestorePhoneNumber(){
        String phone = pref.getString(activity.getString(R.string.phone_key), null);
        return phone;
    }
}
