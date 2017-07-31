package gallery.zicure.company.com.modellibrary.utilize;

import gallery.zicure.company.com.modellibrary.models.AuthToken;
import gallery.zicure.company.com.modellibrary.models.DateModel;
import gallery.zicure.company.com.modellibrary.models.KeyModel;
import gallery.zicure.company.com.modellibrary.models.bloc.ResponseBlocUser;
import gallery.zicure.company.com.modellibrary.models.profile.ResponseIDCard;

/**
 * Created by 4GRYZ52 on 10/25/2016.
 */

public class ModelCart {
    private static ModelCart me = null;
    private ListModel listModel = null;
    private KeyModel keyModel = null;
    private AuthToken authToken = null;
    private ResponseBlocUser userInfo = null;
    private ResponseIDCard.ResultProfile.ProfileData responseIDCard = null;

    private ModelCart() {
        listModel = new ListModel();
        listModel.dateModel = new DateModel();
        keyModel = new KeyModel();
        authToken = new AuthToken();
        userInfo = new ResponseBlocUser();
        responseIDCard = new ResponseIDCard.ResultProfile.ProfileData();
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

    public ResponseIDCard.ResultProfile.ProfileData getProfile(){
        return responseIDCard;
    }

    public void setProfile(ResponseIDCard.ResultProfile.ProfileData profile) {
        responseIDCard = profile;
    }
}
