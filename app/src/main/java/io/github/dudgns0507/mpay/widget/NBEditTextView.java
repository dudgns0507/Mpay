package io.github.dudgns0507.mpay.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by pyh42 on 2017-07-27.
 */

public class NBEditTextView extends android.support.v7.widget.AppCompatEditText {
    public NBEditTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "NanumBarunGothic.otf");
        this.setTypeface(face);
    }

    public NBEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "NanumBarunGothic.otf");
        this.setTypeface(face);
    }

    public NBEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "NanumBarunGothic.otf");
        this.setTypeface(face);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
