package csc133.towerdefense.game;

import android.content.Context;

import android.graphics.PointF;
import android.util.Log;
import java.util.ArrayList;
import java.util.logging.Level;

import csc133.towerdefense.game.GameEngine;
import csc133.towerdefense.game.gameobject.GameObject;

public final class LevelManager {
    static int PLAYER_INDEX = 0;
    private ArrayList<GameObject> objects;
    private Level currentLevel;
    private GameObjectFactory factory;
    LevelManager(Context context,
                 GameEngine ge,
                 int pixelsPerMetre){

        objects = new ArrayList<>();
        factory = new GameObjectFactory(context,
                ge,
                pixelsPerMetre);
    }
}