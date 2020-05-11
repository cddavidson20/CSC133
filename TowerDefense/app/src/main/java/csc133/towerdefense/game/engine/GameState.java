package csc133.towerdefense.game.engine;

import android.content.Context;

import csc133.towerdefense.game.engine.interfaces.IEngineController;

public final class GameState {
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;

    // This object will have access to the deSpawnReSpawn method
    // in GameEngine- once it is initialized
    private IEngineController engineController;

    public long startTimeInMillis;

    public int lives;
    public int gold;
    public int currentWave;
    public int enemiesRemaining;
    public int currentLevel;

    GameState(IEngineController ec, Context context){
        // This initializes the gameStarter reference
        engineController = ec;
    }

    private void endGame(){
        mGameOver = true;
        mPaused = true;
    }

    void reset(){
        currentLevel = 0;
        currentWave = 0;
        gold = 0;
        lives = 10;
    }

    void loseLife(SoundEngine se){
        lives--;
        se.playPlayerExplode();
        if(lives == 0){
            pause();
            endGame();
        }
    }

    void death() {
        stopEverything();
        SoundEngine.playPlayerBurn();
    }

    void startNewGame() {
        stopEverything();
        engineController.startNewLevel();
        startEverything();
        startTimeInMillis = System.currentTimeMillis();
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

    private void startEverything() {
        mPaused = false;
        mGameOver = false;
        mDrawing = true;
    }

    boolean getThreadRunning(){
        return mThreadRunning;
    }
    void startThread(){
        mThreadRunning = true;
    }
    void stopThread() {
        mThreadRunning = false;
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
    public boolean getGameOver(){
        return mGameOver;
    }
    public int getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int level) {
        currentLevel = level;
    }

}
