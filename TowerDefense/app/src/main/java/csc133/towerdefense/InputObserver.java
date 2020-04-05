package csc133.towerdefense;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import csc133.towerdefense.GameObjects.GameObject;

public interface InputObserver {

    void handleInput(MotionEvent event, GameState gs, GameObject go, ArrayList<Rect> controls);
}