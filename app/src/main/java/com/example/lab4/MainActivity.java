package com.example.lab4;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IGameManager {

    private Context context;

    private float dx, dy;

    private ArrayList<View> applesList;
    private ArrayList<View> trashList;

    private TextView appleCounterText;
    private int appleCounter;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        applesList = new ArrayList<>();
        trashList = new ArrayList<>();

        context = this;


        final Button restartButton = findViewById(R.id.restartButton);

        restartButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                AnimatorSet set;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        set = (AnimatorSet) AnimatorInflater.loadAnimator(context,
                                R.animator.restart_button_pressed);
                        set.setTarget(restartButton);
                        set.start();

                        break;
                    case MotionEvent.ACTION_UP:
                        set = (AnimatorSet) AnimatorInflater.loadAnimator(context,
                                R.animator.restart_button);
                        set.setTarget(restartButton);
                        set.start();
                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                recreate();
                            }
                        });

                        break;
                }
                return true;
            }
        });


        appleCounterText = findViewById(R.id.appleCounter);
    }


    @Override
    public void addToAppleList(View view) {
        applesList.add(view);
    }

    @Override
    public ArrayList<View> getApplesList() {
        return applesList;
    }

    @Override
    public void CollectApple(final View apple) {

        appleCounter++;
        appleCounterText.setText(String.valueOf(appleCounter));
        apple.setAlpha(0);

        if (applesList.size() == 0)
            Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void addToTrashList(View view) {
        trashList.add(view);
    }

    @Override
    public ArrayList<View> getTrashList() {
        return trashList;
    }

    @Override
    public void removeTrash(final View view) {
        trashList.remove(view);
        view.setAlpha(0);
    }
}
