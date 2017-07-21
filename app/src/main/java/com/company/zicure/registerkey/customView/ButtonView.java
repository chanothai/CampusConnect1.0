package com.company.zicure.registerkey.customView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.company.zicure.registerkey.R;

/**
 * Created by Pakgon on 7/21/2017 AD.
 */

public class ButtonView extends AppCompatButton {
    public ButtonView(Context context) {
        super(context);
        setFont();
    }

    public ButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public ButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/supermarket.ttf");
        setTypeface(typeface);
        setBackgroundResource(R.drawable.selector_btn_register);
        setTextColor(getResources().getColor(android.R.color.white));
        setCompoundDrawablePadding((int) getResources().getDimension(R.dimen.padding_large));

        setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.size_text_medium));
    }
}
