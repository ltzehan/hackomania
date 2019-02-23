package qihaoooooo.kyoho.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class PixelTextView extends android.support.v7.widget.AppCompatTextView {

    public PixelTextView(Context context) {
        super(context);
        styleText();
    }
    public PixelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        styleText();
    }
    public PixelTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        styleText();
    }

    private void styleText() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/BodgeB.ttf");
        setTypeface(font, Typeface.NORMAL);
    }

}