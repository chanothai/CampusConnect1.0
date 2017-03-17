package gallery.zicure.company.com.gallery.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Display;

/**
 * Created by 4GRYZ52 on 3/14/2017.
 */

public class ScreenOrientation {
    private Activity context = null;

    public ScreenOrientation(Activity context){
        this.context = context;
    }

    public int getOrientation(){
        int orientation = 0;
        Display getOrient = context.getWindowManager().getDefaultDisplay();

        if(getOrient.getWidth() < getOrient.getHeight()){
            orientation = Configuration.ORIENTATION_PORTRAIT;
        }else {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
        }
        return orientation;
    }
}
