package qihaoooooo.kyoho.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ValueBar extends RelativeLayout {

    private ImageView progressBar;

    private int maxValue;
    private int currentValue;

    private int fill;

    public ValueBar(Context context) {
        super(context);
    }

    public ValueBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValueBar(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        progressBar = (ImageView) this.getChildAt(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        progressBar.getDrawable().setLevel(fill);
    }

    public void setBarProp(int maxValue, int currentValue) {
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        update();
    }

    public void decrValue(int v) {
        currentValue -= v;
        if (currentValue < 0) {
            currentValue = 0;
        }
        update();
    }

    public void incrValue(int v) {
        currentValue += v;
        if (currentValue > maxValue) {
            currentValue = maxValue;
        }
        update();
    }

    private void update() {
        fill = (int) (1 - (float) currentValue / maxValue) * 10000;
    }

}