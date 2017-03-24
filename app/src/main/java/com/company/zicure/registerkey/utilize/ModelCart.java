package com.company.zicure.registerkey.utilize;

import com.company.zicure.registerkey.models.DateModel;
import com.company.zicure.registerkey.models.KeyModel;
import com.company.zicure.registerkey.models.LocationModel;
import com.company.zicure.registerkey.models.AuthToken;
import com.company.zicure.registerkey.models.ResponseUserCode;
import com.company.zicure.registerkey.models.ResponseUserInfo;

import org.parceler.Parcel;

/**
 * Created by 4GRYZ52 on 10/25/2016.
 */

public class ModelCart {
    private static ModelCart me = null;
    private ListModel listModel = null;
    private KeyModel keyModel = null;
    private AuthToken authToken = null;
    private ResponseUserInfo userInfo = null;
    private ResponseUserCode deviceToken = null;

    private ModelCart() {
        listModel = new ListModel();
        listModel.locationModel = new LocationModel();
        listModel.dateModel = new DateModel();
        keyModel = new KeyModel();
        authToken = new AuthToken();
        userInfo = new ResponseUserInfo();
        deviceToken = new ResponseUserCode();
    }

    public static ModelCart getInstance(){
        if (me == null){
            me = new ModelCart();
        }

        return me;
    }

    public ListModel getModel(){
        return listModel;
    }

    public KeyModel getKeyModel(){
        return keyModel;
    }

    public AuthToken getAuthToken(){
        return authToken;
    }

    public ResponseUserInfo getUserInfo(){
        return userInfo;
    }

    public ResponseUserCode getDeviceToken(){
        return deviceToken;
    }

}
