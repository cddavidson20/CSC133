package csc133.towerdefense.game.interfaces;

import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

import csc133.towerdefense.game.GameState;

public interface IInputObserver {
    void handleInput(MotionEvent event, GameState gs,
                     ArrayList<Rect> controls);
}