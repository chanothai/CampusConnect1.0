package gallery.zicure.company.com.modellibrary.utilize;

import gallery.zicure.company.com.modellibrary.models.AuthToken;
import gallery.zicure.company.com.modellibrary.models.CategoryModel;
import gallery.zicure.company.com.modellibrary.models.DateModel;
import gallery.zicure.company.com.modellibrary.models.KeyModel;
import gallery.zicure.company.com.modellibrary.models.LocationModel;
import gallery.zicure.company.com.modellibrary.models.ResponseUserCode;
import gallery.zicure.company.com.modellibrary.models.ResponseUserInfo;

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
    private CategoryModel categoryModel = null;

    private ModelCart() {
        listModel = new ListModel();
        listModel.locationModel = new LocationModel();
        listModel.dateModel = new DateModel();
        keyModel = new KeyModel();
        authToken = new AuthToken();
        userInfo = new ResponseUserInfo();
        deviceToken = new ResponseUserCode();
        categoryModel = new CategoryModel();
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

    public ResponseUserInfo getUserInfo(){
        return userInfo;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void instanceCategory(){
        categoryModel = new CategoryModel();
    }

    public ResponseUserCode getDeviceToken(){
        return deviceToken;
    }

}
