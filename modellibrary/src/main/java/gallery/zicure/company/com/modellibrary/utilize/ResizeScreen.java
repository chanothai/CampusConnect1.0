package gallery.zicure.company.com.modellibrary.utilize;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by 4GRYZ52 on 3/14/2017.
 */

public class ResizeScreen {
    private DisplayMetrics metrics = null;

    public ResizeScreen(Activity activity){
        metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }


    public int heightScreen(int originalHeight){
        return metrics.heightPixels / originalHeight;
    }

    public int widthScreen(int originalWidth){
        return metrics.widthPixels / originalWidth;
    }
}
