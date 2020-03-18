package csc133.towerdefense;

import android.content.Context;
import android.content.SharedPreferences;

final class GameState {
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;


    // This is how we will make all the high scores persist
    private SharedPreferences.Editor mEditor;

    GameState(Context context){
        // This initializes the gameStarter reference
        //gameStarter = gs;

        // Get the current high score
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("HiScore",
                Context.MODE_PRIVATE);

        // Initialize the mEditor ready
        mEditor = prefs.edit();

    }

    private void endGame(){
        mGameOver = true;
        mPaused = true;
    }

    void startNewGame(){
        // Don'mTransform want to be drawing objects while deSpawnReSpawn is
        // clearing ArrayList and filling it up again
        stopDrawing();
        resume();

        // Now we can draw again
        startDrawing();
    }

    void pause(){
        mPaused = true;
    }

    void resume(){
        mGameOver = false;
        mPaused = false;
    }

    void stopEverything(){
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    boolean getThreadRunning(){
        return mThreadRunning;
    }

    void startThread(){
        mThreadRunning = true;
    }

    private void stopDrawing(){
        mDrawing = false;
    }

    private void startDrawing(){
        mDrawing = true;
    }

    boolean getDrawing() {return mDrawing;}

    boolean getPaused(){
        return mPaused;
    }

    boolean getGameOver(){
        return mGameOver;
    }

}
