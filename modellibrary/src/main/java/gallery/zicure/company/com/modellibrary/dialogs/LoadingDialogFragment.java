package gallery.zicure.company.com.modellibrary.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import gallery.zicure.company.com.modellibrary.R;

/**
 * Created by 4GRYZ52 on 10/21/2016.
 */
public class LoadingDialogFragment extends DialogFragment {

    public static LoadingDialogFragment newInstance(){
        return new LoadingDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);

        return inflater.inflate(R.layout.dialog_loading, container);
    }

    public static class Builder{
        public Builder() { }

        public LoadingDialogFragment build() {
            return LoadingDialogFragment.newInstance();
        }
    }
}
