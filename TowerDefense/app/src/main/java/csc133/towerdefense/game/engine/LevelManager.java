package csc133.towerdefense.game.engine;

import android.content.Context;

import java.util.ArrayList;
import java.util.logging.Level;

import csc133.towerdefense.game.engine.GameEngine;
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

    void setCurrentLevel(String level){
        switch (level) {
            case "underground":
                currentLevel = new LevelUnderground();
                break;

            case "city":
                currentLevel = new LevelCity();
                break;

            case "mountains":
                currentLevel = new LevelMountains();
                break;
        }
    }

    ArrayList<GameObject> getGameObjects(){
        return objects;
    }
}