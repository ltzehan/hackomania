package qihaoooooo.kyoho.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

// TODO extend ProgressBar

public class ValueBar extends ProgressBar {

    private int maxValue;
    private int currentValue;

    public ValueBar(Context context) {
        super(context);
    }

    public ValueBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void init(int maxValue, int currentValue) {
        this.maxValue = maxValue;
        this.currentValue = currentValue;


    }

    public void decrValue(int v) {
        currentValue -= v;
        update();
    }

    public void incrValue(int v) {
        currentValue += v;
        update();
    }

    private void update() {
        int fill = currentValue / maxValue;
        super.setProgress(fill);
    }

}