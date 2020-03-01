package com.example.snake;

//This is meant as a universal class to track
// the state of the game and the score
class PlayState {

    volatile boolean mPlaying = false;
    volatile boolean mPaused = true;
    int mScore;

    PlayState(){}

}
