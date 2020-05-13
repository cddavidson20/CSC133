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

        // level 0 ----
        // wave 0

        ArrayList<Pair<Integer, String>> lv0wave0 = new ArrayList<>();
        lv0wave0.add(new Pair<>(1, "BasicAlien"));
        lv0wave0.add(new Pair<>(30, "MidGradeAlien"));

        // wave 1
        ArrayList<Pair<Integer, String>> lv0wave1 = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            lv0wave1.add(new Pair<>(i * GameEngine.MAX_FPS / 2, "BasicAlien"));
        }
        for (int i = 0; i < 10; ++i) {
            lv0wave1.add(new Pair<>(i * GameEngine.MAX_FPS * 2 / 3, "MidGradeAlien"));
        }

        // wave 2
        ArrayList<Pair<Integer, String>> lv0wave2 = new ArrayList<>();
        for (int i = 0; i < 50; ++i) {
            lv0wave2.add(new Pair<>(i * GameEngine.MAX_FPS / 5, "BasicAlien"));
        }
        for (int i = 0; i < 5; ++i) {
            lv0wave2.add(new Pair<>(i * GameEngine.MAX_FPS / 3, "MidGradeAlien"));
        }
        lv0wave2.add(new Pair<>(400, "HighGradeAlien"));


        // level 1 ----
        // wave 0
        ArrayList<Pair<Integer, String>> lv1wave0 = new ArrayList<>();
        for (int i = 0; i < 50; ++i) {
            lv1wave0.add(new Pair<>(i * GameEngine.MAX_FPS / 3, "BasicAlien"));
        }
        for (int i = 0; i < 20; ++i) {
            lv1wave0.add(new Pair<>(i * GameEngine.MAX_FPS, "MidGradeAlien"));
        }

        // wave 1
        ArrayList<Pair<Integer, String>> lv1wave1 = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            lv1wave1.add(new Pair<>(i * GameEngine.MAX_FPS / 2, "HighGradeAlien"));
        }

        // wave 2
        ArrayList<Pair<Integer, String>> lv1wave2 = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            lv1wave2.add(new Pair<>(i * GameEngine.MAX_FPS / 4, "MidGradeAlien"));
        }
        for (int i = 0; i < 15; ++i) {
            lv1wave2.add(new Pair<>(i * GameEngine.MAX_FPS / 2, "HighGradeAlien"));
        }

        // level 2 ---- FINAL LEVEL
        // wave 0
        ArrayList<Pair<Integer, String>> lv2wave0 = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            lv2wave0.add(new Pair<>(i * GameEngine.MAX_FPS / 2, "BasicAlien"));
        }
        for (int i = 0; i < 20; ++i) {
            lv2wave0.add(new Pair<>(i * GameEngine.MAX_FPS, "BasicAlien"));
        }
        // wave 1
        ArrayList<Pair<Integer, String>> lv2wave1 = new ArrayList<>();
        for (int i = 0; i < 15; ++i) {
            lv2wave1.add(new Pair<>(i * GameEngine.MAX_FPS / 5, "BasicAlien"));
        }
        for (int i = 0; i < 15; ++i) {
            lv2wave1.add(new Pair<>(i * GameEngine.MAX_FPS / 2, "MidGradeAlien"));
        }
        // wave 2
        ArrayList<Pair<Integer, String>> lv2wave2 = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            lv2wave2.add(new Pair<>(i * GameEngine.MAX_FPS / 5, "BasicAlien"));
        }
        for (int i = 0; i < 20; ++i) {
            lv2wave2.add(new Pair<>(i * GameEngine.MAX_FPS, "MidGradeAlien"));
        }
        for (int i = 0; i < 10; ++i) {
            lv2wave2.add(new Pair<>(i * GameEngine.MAX_FPS * 4, "HighGradeAlien"));
        }


        // add waves into a wave array for each level.
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
