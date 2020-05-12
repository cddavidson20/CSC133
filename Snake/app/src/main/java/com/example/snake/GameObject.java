package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;
import java.util.Vector;

class GameObject {

    private Vector<Apple> mApples;

    // The range of values we can choose from
    // to spawn an goodApple
    private PointP mSpawnRange;

    private Sounds sound;

    private Context context;
    private int blockSize;

    Snake mSnake;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    GameObject(){}

    GameObject(Context context, Point size) {

        //define the context so that apples can use it when made
        this.context = context;
        // Work out how many pixels each block is
        this.blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;


        mApples = new Vector<>();
        mSpawnRange = new PointP(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        this.mSnake = new Snake(context, new PointP(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);

        sound = new Sounds(context);
    }

    //draw the snake AND the apples in the vector
    void draw(Canvas mCanvas, Paint mPaint) {
        for (int i = 0; i < mApples.size(); i++) {
            mApples.get(i).draw(mCanvas, mPaint);
        }
        mSnake.draw(mCanvas, mPaint);
    }

    void newGame(){
        // reset the snake
        mSnake.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        //make sure the apples are cleared
        mApples.clear();

        // Get the first apple ready for dinner
        Apple apple = new AppleBuilder(
                        context, mSpawnRange, blockSize,
                        new PointP(), 1
                        ).build();

        mApples.add(apple);
        mApples.get(0).spawn();
    }

    void update(PlayState playState) {
        // Move the snake
        mSnake.move();

        Random rand = new Random();

        // This is the logic for the apples
        // looping through and building new apples etc.
        for (int i = 0; i < mApples.size(); i++) {
            Apple apple = mApples.get(i);
            if (mSnake.checkDinner(apple.getLocation())) {
                int previousScore = playState.mScore;
                apple.updateScore(playState, sound);

                int appleID = rand.nextInt(3) + 1;
                apple = new AppleBuilder(
                        context, mSpawnRange, blockSize,
                        new PointP(), appleID
                        ).build();
                mApples.set(i, apple);
                mApples.get(i).spawn();

                if (determinePassedMultipleOfFive(previousScore, playState.mScore)){
                    if (rand.nextDouble() < 0.2) {
                        Apple newApple = new AppleBuilder(
                                context, mSpawnRange, blockSize,
                                new PointP(), 0
                                ).build();
                        mApples.add(newApple);
                        mApples.get(mApples.size()-1).spawn();
                    } else {
                        appleID = rand.nextInt(3) + 1;
                        Apple newApple = new AppleBuilder(
                                context, mSpawnRange, blockSize,
                                new PointP(), appleID
                                ).build();
                        mApples.add(newApple);
                        mApples.get(mApples.size()-1).spawn();
                    }
                }
            }
        }

        // Did the snake die?
        if (mSnake.detectDeath()) {
            // Pause the game ready to start again
            sound.playCrash();
            playState.mPaused = true;
        }
    }

    // Sometimes points can go from say 3 to 6 skipping 5
    // This is meant to check that case and return true
    // if it passed a multiple of 5
    private boolean determinePassedMultipleOfFive(int previousScore, int currentScore) {
        if (((previousScore + 1) % 5) == 0 && (previousScore + 1) <= currentScore) {
            return true;
        } else if (((previousScore + 2) % 5) == 0 && (previousScore + 2) <= currentScore) {
            return true;
        } else if (((previousScore + 3) % 5) == 0 && (previousScore + 3) <= currentScore) {
            return true;
        } else {
            return false;
        }
    }

}
