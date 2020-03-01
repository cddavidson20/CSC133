package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class GameObject {

    private AppleBuilder mGoodApple;
    private AppleBuilder mBadApple;

    private Sounds sound;

    Snake mSnake;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    GameObject(){}

    GameObject(Context context, Point size) {

        // Work out how many pixels each block is
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        this.mGoodApple = new GoodApple(context, new PointP(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        this.mBadApple = new BadApple(context, new PointP(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);

        this.mSnake = new Snake(context, new PointP(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);

        sound = new Sounds(context);
    }

    void draw(Canvas mCanvas, Paint mPaint) {
        mGoodApple.draw(mCanvas, mPaint);
        mBadApple.draw(mCanvas, mPaint);
        mSnake.draw(mCanvas, mPaint);
    }

    void newGame(){
        // reset the snake
        mSnake.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Get the goodApple ready for dinner
        mGoodApple.spawn();
    }

    void update(PlayState playState) {
        // Move the snake
        mSnake.move();

        // Did the head of the snake eat the apple?
        if (mSnake.checkDinner(mGoodApple.getLocation())) {
            // This reminds me of Edge of Tomorrow.
            // One day the goodApple will be ready!

            Random rand = new Random();

            if (rand.nextDouble() < 0.2) {
                //System.out.println("Bad Apple");
                System.out.println(rand.nextDouble());
                mBadApple.spawn();
            } else {
                //System.out.println("Good Apple");
                mGoodApple.spawn();
                int appleScore = rand.nextInt(3) + 1;
                System.out.println("apple score: " + appleScore);
                playState.mScore += (appleScore);
            }

            // Play a sound
            sound.playEatGood();
        }

        if (mSnake.checkDinner(mBadApple.getLocation())) {
            playState.mScore -= 2;
            mBadApple.despawn();
            sound.playEatBad();
        }

        // Did the snake die?
        if (mSnake.detectDeath()) {
            // Pause the game ready to start again
            sound.playCrash();

            playState.mPaused = true;
        }
    }

}
