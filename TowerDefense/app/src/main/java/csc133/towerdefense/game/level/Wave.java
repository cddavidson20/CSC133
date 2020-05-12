package csc133.towerdefense.game.level;

import android.util.Pair;

import java.util.ArrayList;

public class Wave {
    public ArrayList<Pair<Integer, String>> spawns;
    public Wave(ArrayList<Pair<Integer, String>> spawns) {
        this.spawns = spawns;
    }
}
