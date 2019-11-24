package com.example.lab4;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Trash extends ImageView {
    private VelocityTracker tracker;
    private float dx;
    private float startX;
    private float xThreshold = 100;

    private IGameManager gameManager;

    private View thisView;

    public Trash(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        thisView = this;

        final Context context1 = context;

        setImageDrawable(getResources().getDrawable(R.drawable.trash, null));
        if (isInEditMode())
            return;

        gameManager = ((IGameManager) context);
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gameManager.addToTrashList(thisView);
                thisView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(200, 200);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getRawX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (tracker == null)
                    tracker = VelocityTracker.obtain();
                else
                    tracker.clear();
                tracker.addMovement(event);

                dx = x - getX();
                startX = getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(startX - (x - dx)) < xThreshold) {
                    setX(x - dx);
                }
                tracker.addMovement(event);
                break;
            case MotionEvent.ACTION_UP:
                tracker.addMovement(event);
                tracker.computeCurrentVelocity(1);
                if (Math.abs(tracker.getXVelocity() / tracker.getYVelocity()) > 2 && Math.abs(tracker.getXVelocity()) > 2) {
                    Toast.makeText(getContext(), "Swipe!", Toast.LENGTH_SHORT).show();
                    animate().translationXBy(Math.signum(tracker.getXVelocity()) * 1000).setDuration(700).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            gameManager.removeTrash(thisView);
                        }
                    }).start();
                    tracker.recycle();
                } else
                    setX(startX);
                break;
        }

        return true;
    }
}
