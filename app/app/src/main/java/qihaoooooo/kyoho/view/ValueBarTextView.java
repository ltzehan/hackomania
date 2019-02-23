package qihaoooooo.kyoho.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class ValueBarTextView extends android.support.v7.widget.AppCompatTextView {

    public ValueBarTextView(Context context) {
        super(context);
        setFont();
    }
    public ValueBarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public ValueBarTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Consolas.ttf");
        setTypeface(font, Typeface.NORMAL);
    }

}
