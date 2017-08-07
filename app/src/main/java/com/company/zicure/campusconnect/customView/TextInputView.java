package com.company.zicure.campusconnect.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

/**
 * Created by Pakgon on 7/21/2017 AD.
 */

public class TextInputView extends TextInputLayout {
    public TextInputView(Context context) {
        super(context);
        setFont();
    }

    public TextInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public TextInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/supermarket.ttf");
        setTypeface(typeface);
    }
}
