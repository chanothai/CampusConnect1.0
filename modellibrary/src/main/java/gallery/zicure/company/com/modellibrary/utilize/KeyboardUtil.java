package gallery.zicure.company.com.modellibrary.utilize;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by 4GRYZ52 on 2/16/2017.
 */

public class KeyboardUtil {
    private InputMethodManager keySoft = null;
    private static KeyboardUtil me = null;

    public KeyboardUtil(Context context){
        keySoft = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static KeyboardUtil newInstance(Context context){
        if (me == null){
            me = new KeyboardUtil(context);
        }
        return me;
    }

    public InputMethodManager getKeySoft(){
        return keySoft;
    }
}
