package com.company.zicure.campusconnect.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.company.zicure.campusconnect.R;

/**
 * Created by Pakgon on 7/21/2017 AD.
 */

public class EditTextView extends TextInputEditText {

    public EditTextView(Context context) {
        super(context);
        setFont();
    }

    public EditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public EditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/supermarket.ttf");
        setTypeface(typeface);

        setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.size_text_medium));
    }
}
