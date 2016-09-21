package com.feicui.learntocook.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lenovo on 2016/9/9.
 */
public class LogoTextView extends TextView {


    public LogoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts.BB.TTF");
    }


}
