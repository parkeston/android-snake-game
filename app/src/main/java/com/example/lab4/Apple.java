package com.example.lab4;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class Apple extends ImageView {
    private float dx, dy;

    public Apple(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        final View v = this;

        final Context context1 = context;

        setImageDrawable(getResources().getDrawable(R.drawable.apple, null));

        if (isInEditMode())
            return;

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((IGameManager) context1).addToAppleList(v);
                v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(100, 100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dx = x - getX();
                dy = y - getY();
                break;
            case MotionEvent.ACTION_MOVE:
                setX(x - dx);
                setY(y - dy);
                break;
        }

        return true;
    }
}
