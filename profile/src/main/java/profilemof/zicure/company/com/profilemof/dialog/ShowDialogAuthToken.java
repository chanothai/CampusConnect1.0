package profilemof.zicure.company.com.profilemof.dialog;

import android.app.Activity;
import android.app.Dialog;

/**
 * Created by 4GRYZ52 on 3/29/2017.
 */

public class ShowDialogAuthToken {
    private Activity activity = null;

    public ShowDialogAuthToken(Activity activity){
        this.activity = activity;
    }

    public void createDialog(){
        Dialog dialog = new Dialog(activity);

    }
}
