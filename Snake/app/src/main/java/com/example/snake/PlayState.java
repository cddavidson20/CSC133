package com.example.snake;

//This is meant as a universal class to track
// the state of the game and the score
class PlayState {

    volatile boolean mPlaying;
    volatile boolean mPaused;
    int mScore;

    PlayState(){
        mPlaying = false;
        mPaused = true;
        mScore = 0;
    }

}
