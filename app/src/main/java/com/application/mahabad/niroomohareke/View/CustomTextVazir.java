package com.application.mahabad.niroomohareke.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextVazir extends TextView {

    public CustomTextVazir(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CustomTextVazir(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextVazir(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/vazir.ttf");
        setTypeface(tf);
    }
}


