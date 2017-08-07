package com.company.zicure.campusconnect.utility;

/**
 * Created by 4GRYZ52 on 3/16/2017.
 */

public class PermissionKeyNumber {
    private static PermissionKeyNumber me = null;

    public static PermissionKeyNumber getInstance(){
        if (me == null){
            me = new PermissionKeyNumber();
        }
        return me;
    }

    public int getPermissionCameraKey(){
        return 112;
    }

    public int getPermissionReadStorageKey(){
        return 111;
    }
}
