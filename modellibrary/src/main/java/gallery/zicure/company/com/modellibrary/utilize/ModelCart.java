package gallery.zicure.company.com.modellibrary.utilize;

import gallery.zicure.company.com.modellibrary.models.AuthToken;
import gallery.zicure.company.com.modellibrary.models.DateModel;
import gallery.zicure.company.com.modellibrary.models.KeyModel;
import gallery.zicure.company.com.modellibrary.models.LocationModel;
import gallery.zicure.company.com.modellibrary.models.ResponseUserCode;
import gallery.zicure.company.com.modellibrary.models.bloc.ResponseBlocUser;

/**
 * Created by 4GRYZ52 on 10/25/2016.
 */

public class ModelCart {
    private static ModelCart me = null;
    private ListModel listModel = null;
    private KeyModel keyModel = null;
    private AuthToken authToken = null;
    private ResponseBlocUser userInfo = null;
    private ResponseUserCode deviceToken = null;

    private ModelCart() {
        listModel = new ListModel();
        listModel.locationModel = new LocationModel();
        listModel.dateModel = new DateModel();
        keyModel = new KeyModel();
        authToken = new AuthToken();
        userInfo = new ResponseBlocUser();
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

    public AuthToken getAuth(){
        return authToken;
    }

    public ResponseBlocUser getUserBloc(){
        return userInfo;
    }

    public ResponseUserCode getDeviceToken(){
        return deviceToken;
    }

}
