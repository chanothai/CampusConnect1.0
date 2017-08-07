package com.company.zicure.campusconnect.utility;

import android.Manifest;
import android.app.Activity;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class PermissionRequest {
    private Activity context = null;
    private PermissionManager manager = null;


    public PermissionRequest(Activity context){
        this.context = context;
        manager = new PermissionManager(context);
    }

    public boolean requestCamera(){
        if (!manager.checkPermission(Manifest.permission.CAMERA, PermissionKeyNumber.getInstance().getPermissionCameraKey())){
            return false;
        }
        return true;
    }

    public boolean requestReadStorage(){
        if (!manager.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,PermissionKeyNumber.getInstance().getPermissionReadStorageKey())){
            return false;
        }
        return true;
    }

    public boolean requestCallPhone() {
        if (!manager.checkPermission(Manifest.permission.CALL_PHONE, 113)){
            return false;
        }

        return true;
    }
}
