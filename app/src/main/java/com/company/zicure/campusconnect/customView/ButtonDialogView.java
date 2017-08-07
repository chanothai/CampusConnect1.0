package com.company.zicure.campusconnect.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * Created by Pakgon on 7/25/2017 AD.
 */

public class ButtonDialogView extends AppCompatButton {
    public ButtonDialogView(Context context) {
        super(context);
        setButton();
    }

    public ButtonDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setButton();
    }

    public ButtonDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setButton();
    }

    private void setButton() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/supermarket.ttf");
        setTypeface(typeface);
    }
}
