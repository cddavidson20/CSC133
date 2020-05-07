package csc133.towerdefense;

import android.content.Context;
import android.content.SharedPreferences;

public class GameState {
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;
    private static volatile boolean passedWave = true;
    private static volatile boolean newGame = true;


    private static int wave = 0;
    private int gold = 200;
    private int lives = 10;

    // This is how we will make all the high scores persist
    private SharedPreferences.Editor mEditor;

    GameState(Context context) {
        // This initializes the gameStarter reference
        //gameStarter = gs;

        // Get the current high score
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("HiScore",
                Context.MODE_PRIVATE);

        // Initialize the mEditor ready
        mEditor = prefs.edit();

    }

    private void endGame() {
        mGameOver = true;
        mPaused = true;
    }

    public void reset() {
        wave = 0;
        gold = 200;
        lives = 10;
    }

    void startNewGame() {
        // Don'mTransform want to be drawing objects while deSpawnReSpawn is
        // clearing ArrayList and filling it up again
        stopDrawing();
        resume();

        // Now we can draw again
        startDrawing();
    }

    boolean newGame() {
        return newGame;
    }

    void toggleNewGame() {
        newGame = false;
    }

    void pause() {
        mPaused = true;
    }

    public void passedWave() {
        passedWave = true;
    }

    void resume() {
        mGameOver = false;
        mPaused = false;
    }

    void stopEverything() {
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    boolean getThreadRunning() {
        return mThreadRunning;
    }

    void startThread() {
        mThreadRunning = true;
    }

    private void stopDrawing() {
        mDrawing = false;
    }

    private void startDrawing() {
        mDrawing = true;
    }

    boolean getDrawing() {
        return mDrawing;
    }

    boolean getPaused() {
        return mPaused;
    }

    boolean getPassedWave() {
        return passedWave;
    }

    boolean getGameOver() {
        return mGameOver;
    }

    public int getWave() {
        return wave;
    }

    public void incrementWave() {
        wave++;
        // gain 1 life if you passed a wave?
        //incrementLives();
    }

    int getGold() {
        return gold;
    }

    public void gainedGold(int gold) {
        this.gold += gold;
    }

    public void spentGold(int gold) {
        this.gold -= gold;
    }

    public void removeLives() {
        lives--;
        if (lives <= 0) {
            endGame();
        }
    }

    private void incrementLives() {
        lives++;
    }

    public int getLives() {
        return lives;
    }
}
