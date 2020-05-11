package csc133.towerdefense.game;

import android.content.Context;

import csc133.towerdefense.game.interfaces.IGameStarter;

public final class GameState {
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;

    // This object will have access to the deSpawnReSpawn method
    // in GameEngine- once it is initialized
    private IGameStarter gameStarter;

    private int lives;
    private int gold;
    private int wave;
    private int mNumShips;
    public int level;

    GameState(IGameStarter gs, Context context){
        // This initializes the gameStarter reference
        gameStarter = gs;
    }

    private void endGame(){
        mGameOver = true;
        mPaused = true;
    }

    void startNewGame(){
        level = 0;
        wave = 0;
        //mNumShips = 3;

        // Don't want to be drawing objects
        // while deSpawnReSpawn is
        // clearing them and spawning them again
        stopDrawing();
        gameStarter.deSpawnReSpawn();
        resume();

        // Now we can draw again
        startDrawing();
    }

    void loseLife(SoundEngine se){
        lives--;
        se.playPlayerExplode();
        if(lives == 0){
            pause();
            endGame();
        }
    }

    int getNumShips(){
        return mNumShips;
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
    boolean getDrawing() {
        return mDrawing;
    }
    boolean getPaused(){
        return mPaused;
    }
    boolean getGameOver(){
        return mGameOver;
    }

}
