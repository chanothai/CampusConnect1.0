package com.company.zicure.registerkey.utilize;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by 4GRYZ52 on 10/22/2016.
 */
public class PermissionManager {

    private Context context = null;
    private static PermissionManager me;

    public PermissionManager(Context context){
        this.context = context;
    }

    public static PermissionManager getInstance(Context context){
        if (me == null){
            me = new PermissionManager(context);
        }
        return me;
    }

    public boolean checkPermission(String permission, int myPermission){
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, myPermission);
            return true;
        }

        return false;
    }
}
