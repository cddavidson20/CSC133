package csc133.towerdefense.game.level;

import android.graphics.Canvas;
import android.util.Pair;

import java.util.ArrayList;

import csc133.towerdefense.game.Background;
import csc133.towerdefense.game.GameEngine;
import csc133.towerdefense.game.gameobject.enemy.AlienCreator;
import csc133.towerdefense.game.gameobject.enemy.BasicAlienBuilder;
import csc133.towerdefense.game.gameobject.enemy.Enemy;
import csc133.towerdefense.game.gameobject.enemy.MidGradeAlienBuilder;
import csc133.towerdefense.game.gameobject.enemy.SpeedGradeAlienBuilder;
import csc133.towerdefense.game.movepath.MovePath;
import csc133.towerdefense.game.movepath.Paths;

public class LevelManager {
    AlienCreator alienCreator;
    public int currentLevel;
    public int currentWave;
    public ArrayList<Pair<Integer, String>> spawns;
    Level[] levels;
    MovePath[] paths;
    public long waveFrame;
    Background background;

    public MovePath getCurrPath() {
        return paths[currentLevel];
    }

    public LevelManager() {
        levels = Levels.GetLevels();
        paths = Paths.Paths;
        background = new Background();
        init();
    }

    public int getEnemiesRemaining() {
        return spawns.size();
    }

    public void init() {
        currentLevel = 0;
        currentWave = 0;
        updateWave();
    }

    public boolean atFinalStage() {
        return !hasNextWave() && !hasNextLevel();
    }

    public void reset() {
        currentLevel = 0;
        currentWave = 0;
        updateWave();
    }


    public void next() {
        if (hasNextWave()) {
            ++currentWave;
            updateWave();
        } else if (hasNextLevel()) {
            ++currentLevel;
            currentWave = 0;
            updateWave();
        } else {
            System.out.println("Congratulations.. you won");
        }
    }

    public boolean hasNextWave() {
        Level level = levels[currentLevel];
        return currentWave + 1 < level.waves.length;
    }

    public boolean hasNextLevel() {
        return currentLevel + 1 < levels.length;
    }

    public void updateWave() {
        background.initialize("background");
        waveFrame = GameEngine.framesRan;
        Level level = levels[currentLevel];
        Wave wave = level.waves[currentWave];
        this.spawns = (ArrayList<Pair<Integer, String>>) (wave.spawns.clone());
    }

    public void draw(Canvas canvas) {
        background.draw(canvas);
        paths[currentLevel].draw(canvas);
    }


    public void update(ArrayList<Enemy> enemies) {
        for (int i = 0; i < spawns.size(); ++i) {
            // read instruction on what to make and when to make
            Pair<Integer, String> spawn = spawns.get(i);
            int frameToSpawn = spawn.first;
            String unitToSpawn = spawn.second;

            // if the time is to make it now then have to make it now
            if (GameEngine.framesRan - waveFrame == frameToSpawn) {
                // now find out what to make
                switch (unitToSpawn) {
                    case "BasicAlien":
                        alienCreator = new AlienCreator(new BasicAlienBuilder());
                        break;
                    case "MidGradeAlien":
                        alienCreator = new AlienCreator(new MidGradeAlienBuilder());
                        break;
                    case "HighGradeAlien":
                        alienCreator = new AlienCreator(new SpeedGradeAlienBuilder());
                    default:
                        System.err.println("Unknown unit tag: " + unitToSpawn);
                        break;
                }

                // now make
                float startX = paths[currentLevel].startX;
                float startY = paths[currentLevel].startY;
                MovePath currentPath = paths[currentLevel];

                enemies.add(alienCreator.createAlien(startX, startY, currentPath, true));

                // now remove the instruction to create alien
                spawns.remove(i);
                --i;
            }
        }
    }
}
