package gallery.zicure.company.com.gallery.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class PermissionRequest {
    private static PermissionRequest me = null;
    private Activity context = null;


    public PermissionRequest(Activity context){
        this.context = context;
    }

    public static PermissionRequest newInstance(Activity context){
        if (me == null){
            me = new PermissionRequest(context);
        }
        return me;
    }

    public boolean requestCamera(){
        if (!PermissionManager.getInstance(context).checkPermission(Manifest.permission.CAMERA, PermissionKeyNumber.getInstance().getPermissionCameraKey())){
            return false;
        }
        return true;
    }

    public boolean requestReadStorage(){
        if (!PermissionManager.getInstance(context).checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,PermissionKeyNumber.getInstance().getPermissionReadStorageKey())){
            return false;
        }

        return true;
    }
}
