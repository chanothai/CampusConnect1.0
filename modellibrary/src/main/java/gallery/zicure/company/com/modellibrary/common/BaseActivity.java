package gallery.zicure.company.com.modellibrary.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import gallery.zicure.company.com.modellibrary.dialogs.LoadingDialogFragment;

/**
 * Created by 4GRYZ52 on 10/21/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG_DIALOG_FRAGMENT = "dialog_fragment";
    private LoadingDialogFragment loadingDialogFragment = null;


    public void openActivity(Class<?> cls) {
        openActivity(cls,null, false);
    }

    public void openActivity(Class<?> cls, boolean finishActivity) {
        openActivity(cls,null, finishActivity);
    }

    public void openActivity(Class<?> cls, Bundle bundle) {
        openActivity(cls, bundle, false);
    }

    public void openActivity(Class<?> cls,Bundle bundle, boolean finishActivity) {
        if (cls != null){
            Intent intent = new Intent(this, cls);
            if (bundle != null){
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
        if (finishActivity){
            finish();
        }
    }

    public void showLoadingDialog(){
        dismissDialog();
        loadingDialogFragment = new LoadingDialogFragment.Builder().build();
        createFragmentDialog(loadingDialogFragment);
    }

    private void createFragmentDialog(DialogFragment dialogFragment){
        try{
            dialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_FRAGMENT);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    public void dismissDialog(){
        try{
            if (loadingDialogFragment != null){
                loadingDialogFragment.dismiss();
            }
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }
}
