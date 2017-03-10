package com.company.zicure.registerkey.utilize;

import com.company.zicure.registerkey.models.DateModel;
import com.company.zicure.registerkey.models.KeyModel;
import com.company.zicure.registerkey.models.LocationModel;
import com.company.zicure.registerkey.models.UserResponse;

/**
 * Created by 4GRYZ52 on 10/25/2016.
 */
public class ModelCart {

    private static ModelCart me = null;
    private ListModel listModel = null;
    private KeyModel keyModel = null;


    private ModelCart() {
        listModel = new ListModel();
        listModel.locationModel = new LocationModel();
        listModel.dateModel = new DateModel();
        listModel.userResponse = new UserResponse();

        keyModel = new KeyModel();
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
}
