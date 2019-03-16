package com.application.mahabad.niroomohareke.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextVazirBold extends TextView {

    public CustomTextVazirBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CustomTextVazirBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextVazirBold(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/vazir_bold.ttf");
        setTypeface(tf);
    }
}


