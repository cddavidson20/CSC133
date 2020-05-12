package csc133.towerdefense.game.level;

import android.util.Pair;

import java.util.ArrayList;

import csc133.towerdefense.game.GameEngine;

public class Levels {
    static Level[] levels;
    private static boolean initialized = false;

    public static Level[] GetLevels() {
        if (initialized) return levels;
        initialized = true;

        ArrayList<Pair<Integer, String>> lv0wave0 = new ArrayList<>();
        lv0wave0.add(new Pair<>(1, "BasicAlien"));
        lv0wave0.add(new Pair<>(30, "MidGradeAlien"));

        ArrayList<Pair<Integer, String>> lv0wave1 = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            lv0wave1.add(new Pair<>(i * GameEngine.MAX_FPS / 2, "BasicAlien"));
        }
        for (int i = 0; i < 3; ++i) {
            lv0wave1.add(new Pair<>(i * GameEngine.MAX_FPS * 10 / 3, "MidGradeAlien"));
        }

        ArrayList<Pair<Integer, String>> lv0wave2 = new ArrayList<>();


        ArrayList<Pair<Integer, String>> lv1wave0 = new ArrayList<>();
        ArrayList<Pair<Integer, String>> lv1wave1 = new ArrayList<>();
        ArrayList<Pair<Integer, String>> lv1wave2 = new ArrayList<>();


        ArrayList<Pair<Integer, String>> lv2wave0 = new ArrayList<>();
        ArrayList<Pair<Integer, String>> lv2wave1 = new ArrayList<>();
        ArrayList<Pair<Integer, String>> lv2wave2 = new ArrayList<>();


        Wave[] level0Waves = new Wave[]{
                new Wave(lv0wave0), new Wave(lv0wave1), new Wave(lv0wave2)
        };
        Wave[] level1Waves = new Wave[]{
                new Wave(lv1wave0), new Wave(lv1wave1), new Wave(lv1wave2)
        };
        Wave[] level2Waves = new Wave[]{
                new Wave(lv2wave0), new Wave(lv2wave1), new Wave(lv2wave2)
        };

        levels = new Level[]{
                new Level(level0Waves),
                new Level(level1Waves),
                new Level(level2Waves),
        };

        return levels;
    }
}
