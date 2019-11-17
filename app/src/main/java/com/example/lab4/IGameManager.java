package com.example.lab4;

import android.view.View;

import java.util.ArrayList;

public interface IGameManager {
    void addToAppleList(View view);

    ArrayList<View> getApplesList();

    void CollectApple(View apple);

    void addToTrashList(View view);

    ArrayList<View> getTrashList();

    void removeTrash(View view);
}
